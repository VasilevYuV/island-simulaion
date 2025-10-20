package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.config.AnimalConfig;
import com.VasilevYuV.island.location.Location;
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
    protected static final Logger log = LoggerFactory.getLogger(Animal.class); // Добавляем логгер

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

    // Абстрактные методы
    public abstract void eat();

    public void reproduce() {
        // Усиливаем проверки: животное должно быть достаточно сытым и здоровым
        if (!alive || currentLocation == null || satiety < maxFoodRequired * 0.5) return;

        try {
            AnimalConfig config = AnimalConfig.valueOf(this.getClass().getSimpleName().toUpperCase());
            double reproductionProb = config.getReproductionProbability();

            // Дополнительная проверка: должно быть достаточно ресурсов в локации
            boolean hasEnoughResources = true;
            if (this instanceof Herbivore) {
                // Травоядные проверяют наличие растений
                hasEnoughResources = currentLocation.getPlantCount() > 5;
            }

            if (Math.random() < reproductionProb && hasEnoughResources) {
                // Ищем партнеров с еще более строгими условиями
                long potentialPartners = currentLocation.getAliveAnimals().stream()
                        .filter(a -> a.getClass() == this.getClass()
                                && a != this
                                && a.getSatiety() > a.maxFoodRequired * 0.5) // Повышаем порог сытости
                        .count();

                if (potentialPartners > 0) {
                    Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                    if (currentLocation.canAddAnimal(this.getClass())) {
                        currentLocation.addAnimal(baby);
                        baby.setCurrentLocation(currentLocation);

                        // Увеличиваем затраты на размножение
                        this.satiety = Math.max(0, this.satiety - this.maxFoodRequired * 0.3);
                        log.debug("New {} born! Partners: {}", getClass().getSimpleName(), potentialPartners);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error in reproduction for {}", getClass().getSimpleName(), e);
        }
    }

    public abstract boolean canEat(Animal animal);
    public abstract double getEatingProbability(Animal animal);

    // Общие методы
    public void decreaseSatiety() {
        this.satiety = Math.max(0, this.satiety - (maxFoodRequired * 0.1));
        if (this.satiety <= 0) {
            this.alive = false;
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public double getWeight() { return weight; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location location) { this.currentLocation = location; }
    public double getSatiety() { return satiety; }
    public void setSatiety(double satiety) { this.satiety = satiety; }
    public String getType() { return this.getClass().getSimpleName().toLowerCase(); }

    // Добавляем сеттеры для полей (нужны для рефлексии в SimulationEngine)
    public void setWeight(double weight) { this.weight = weight; }
    public void setMaxFoodRequired(double maxFoodRequired) { this.maxFoodRequired = maxFoodRequired; }
    public void setMaxSpeed(int maxSpeed) { this.maxSpeed = maxSpeed; }
}