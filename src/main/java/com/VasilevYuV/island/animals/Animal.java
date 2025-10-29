package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.animals.herbivores.*;
import com.VasilevYuV.island.animals.predators.*;
import com.VasilevYuV.island.config.AnimalConfig;
import com.VasilevYuV.island.location.Location;
import com.VasilevYuV.island.service.IslandService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        // Хищники
        @JsonSubTypes.Type(value = Wolf.class, name = "wolf"),
        @JsonSubTypes.Type(value = Boa.class, name = "boa"),
        @JsonSubTypes.Type(value = Fox.class, name = "fox"),
        @JsonSubTypes.Type(value = Bear.class, name = "bear"),
        @JsonSubTypes.Type(value = Eagle.class, name = "eagle"),

        // Травоядные
        @JsonSubTypes.Type(value = Horse.class, name = "horse"),
        @JsonSubTypes.Type(value = Deer.class, name = "deer"),
        @JsonSubTypes.Type(value = Rabbit.class, name = "rabbit"),
        @JsonSubTypes.Type(value = Mouse.class, name = "mouse"),
        @JsonSubTypes.Type(value = Goat.class, name = "goat"),
        @JsonSubTypes.Type(value = Sheep.class, name = "sheep"),
        @JsonSubTypes.Type(value = Boar.class, name = "boar"),
        @JsonSubTypes.Type(value = Buffalo.class, name = "buffalo"),
        @JsonSubTypes.Type(value = Duck.class, name = "duck"),
        @JsonSubTypes.Type(value = Caterpillar.class, name = "caterpillar")
})
public abstract class Animal {
    private static final AtomicLong idCounter = new AtomicLong(0);
    protected static final Logger log = LoggerFactory.getLogger(Animal.class);

    protected final Long id;
    protected double weight;
    protected double maxFoodRequired;
    protected int maxSpeed;
    protected boolean alive;
    protected double satiety;

    @JsonIgnore
    protected Location currentLocation;

    public Animal(double weight, double maxFoodRequired, int maxSpeed) {
        this.id = idCounter.incrementAndGet();
        this.weight = weight;
        this.maxFoodRequired = maxFoodRequired;
        this.maxSpeed = maxSpeed;
        this.alive = true;
        this.satiety = maxFoodRequired / 1.5;
    }
    public abstract void eat();

    public void reproduce() {
        if (!alive || currentLocation == null) return;

        try {
            AnimalConfig config = AnimalConfig.valueOf(this.getClass().getSimpleName().toUpperCase());
            double baseReproductionProb = config.getReproductionProbability();
            int currentCount = currentLocation.getAnimalsCount(this.getClass());
            int maxCapacity = currentLocation.getMaxAnimalsPerType(this.getClass());

            // РАЗНЫЕ КОЭФФИЦИЕНТЫ ПЕРЕНАСЕЛЕНИЯ
            double overcrowdingFactor = getOvercrowdingFactor(currentCount, maxCapacity);

            // РАЗНЫЕ ПОРОГИ СЫТОСТИ
            double satietyThreshold;
            if (isSmallAnimal()) {
                satietyThreshold = maxFoodRequired * 0.6; // Мелкие должны быть очень сыты
            } else if (this instanceof Predator) {
                satietyThreshold = maxFoodRequired * 0.4; // Хищникам достаточно 40%
            } else {
                satietyThreshold = maxFoodRequired * 0.5; // Крупные травоядные - 50%
            }

            if (satiety < satietyThreshold) return;

            double actualProbability = baseReproductionProb * overcrowdingFactor;

            // ДОПОЛНИТЕЛЬНЫЕ УСЛОВИЯ ДЛЯ МЕЛКИХ ЖИВОТНЫХ
            if (isSmallAnimal()) {
                // Мелкие животные размножаются только если есть достаточно растений
                boolean hasEnoughPlants = currentLocation.getPlantCount() > 10;
                if (!hasEnoughPlants) {
                    actualProbability *= 0.1; // Резко снижаем вероятность
                }
            }

            if (Math.random() < actualProbability && currentLocation.canAddAnimal(this.getClass())) {
                // УПРОЩАЕМ ПОИСК ПАРТНЕРА ДЛЯ ХИЩНИКОВ
                boolean hasPartner;
                if (this instanceof Predator) {
                    // Хищникам достаточно любого партнера того же вида
                    hasPartner = currentLocation.getAliveAnimals().stream()
                            .anyMatch(a -> a.getClass() == this.getClass() && a != this);
                } else {
                    // Травоядные требуют сытого партнера
                    hasPartner = currentLocation.getAliveAnimals().stream()
                            .anyMatch(a -> a.getClass() == this.getClass()
                                    && a != this
                                    && a.getSatiety() > a.maxFoodRequired * 0.4);
                }

                if (hasPartner) {
                    Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                    currentLocation.addAnimal(baby);
                    baby.setCurrentLocation(currentLocation);

                    double energyCost = getEnergyCost();

                    this.satiety = Math.max(0, this.satiety - energyCost);

                    log.debug("{} reproduced! Population: {}/{}, probability: {}",
                            getClass().getSimpleName(), currentCount + 1, maxCapacity, actualProbability);
                }
            }
        } catch (Exception e) {
            log.error("Error in reproduction for {}", getClass().getSimpleName(), e);
        }
    }

    private double getEnergyCost() {
        // РАЗНЫЕ ЗАТРАТЫ ЭНЕРГИИ
        double energyCost;
        if (isSmallAnimal()) {
            energyCost = maxFoodRequired * 0.4; // Мелкие тратят много
        } else if (this instanceof Predator) {
            energyCost = maxFoodRequired * 0.2; // Хищники тратят мало
        } else {
            energyCost = maxFoodRequired * 0.3; // Крупные травоядные - среднее
        }
        return energyCost;
    }

    private double getOvercrowdingFactor(double currentCount, int maxCapacity) {
        double overcrowdingFactor;
        if (isSmallAnimal()) {
            // МЕЛКИЕ ЖИВОТНЫЕ - очень строгие ограничения
            overcrowdingFactor = Math.max(0, 1.0 - (currentCount / (maxCapacity * 0.3)));
        } else if (this instanceof Predator) {
            // ХИЩНИКИ - мягкие ограничения, стимулируем размножение
            overcrowdingFactor = Math.max(0.5, 1.0 - (currentCount / (maxCapacity * 0.8)));
        } else {
            // КРУПНЫЕ ТРАВОЯДНЫЕ - средние ограничения
            overcrowdingFactor = Math.max(0, 1.0 - (currentCount / (maxCapacity * 0.6)));
        }
        return overcrowdingFactor;
    }

    private boolean isSmallAnimal() {
        return this instanceof Rabbit ||
                this instanceof Mouse ||
                this instanceof Duck ||
                this instanceof Caterpillar;
    }

    public abstract boolean canEat(Animal animal);
    public abstract double getEatingProbability(Animal animal);
    public void decreaseSatiety() {
        this.satiety = Math.max(0, this.satiety - (maxFoodRequired * 0.1));
        if (this.satiety <= 0) {
            this.alive = false;
        }
    }

    // Методы для работы с локациями через сервис
    protected Location getLocation(int x, int y) {
        return IslandService.getLocation(x, y);
    }

    protected boolean isValidLocation(int x, int y) {
        return IslandService.isValidLocation(x, y);
    }

    // Getters and setters
    public double getWeight() { return weight; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public void setCurrentLocation(Location location) { this.currentLocation = location; }
    public double getSatiety() { return satiety; }

    // Добавляем сеттеры для полей (нужны для рефлексии в SimulationEngine)
    public void setWeight(double weight) { this.weight = weight; }
    public void setMaxFoodRequired(double maxFoodRequired) { this.maxFoodRequired = maxFoodRequired; }
    public void setMaxSpeed(int maxSpeed) { this.maxSpeed = maxSpeed; }
}