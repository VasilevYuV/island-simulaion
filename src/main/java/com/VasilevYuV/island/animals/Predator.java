package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.animals.predators.Bear;
import com.VasilevYuV.island.config.AnimalConfig;
import com.VasilevYuV.island.location.Location;
import com.VasilevYuV.island.service.IslandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class Predator extends Animal {
    protected static final Logger log = LoggerFactory.getLogger(Predator.class);

    public Predator(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public void eat() {
        if (currentLocation == null || !isAlive()) return;
        move();
        boolean ateSomething = false;
        List<Animal> potentialPrey = currentLocation.getAliveAnimals().stream()
                .filter(animal -> animal != this && animal.isAlive() && canEat(animal))
                .toList();

        for (Animal prey : potentialPrey) {
            double probability = getEatingProbability(prey);
            if (Math.random() * 100 < probability) {
                prey.setAlive(false);
                double nutrition = prey.getWeight() * 0.8;
                this.satiety = Math.min(this.maxFoodRequired, this.satiety + nutrition);
                currentLocation.removeAnimal(prey);
                log.trace("{} ate {} and gained {} nutrition, satiety: {}/{}",
                        this.getClass().getSimpleName(),
                        prey.getClass().getSimpleName(),
                        nutrition,
                        this.satiety, this.maxFoodRequired);
                ateSomething = true;
                break;
            }
        }

        // Если хищник всеядный (медведь), может есть растения
        if (!ateSomething && this instanceof Bear && currentLocation.getPlantCount() > 0) {
            int plantsToEat = Math.min(10, currentLocation.getPlantCount());
            currentLocation.consumePlants(plantsToEat);
            double plantNutrition = plantsToEat * 0.5;
            this.satiety = Math.min(this.maxFoodRequired, this.satiety + plantNutrition);
            log.trace("Bear ate {} plants, gained {} nutrition", plantsToEat, plantNutrition);
        }
    }

    /**
     * Интеллектуальное движение хищника к добыче
     */

    public void move() {
        if (currentLocation == null || !isAlive() || maxSpeed == 0) return;

        AnimalConfig config = AnimalConfig.valueOf(this.getClass().getSimpleName().toUpperCase());
        if (Math.random() > config.getMoveProbability()) {
            return; // Пропускаем движение с вероятностью
        }

        // Если в текущей локации есть добыча - остаемся здесь
        if (hasPreyInCurrentLocation()) {
            log.trace("{} stays in location - prey detected", getClass().getSimpleName());
            return;
        }

        // Ищем ближайшую локацию с добычей в увеличенном радиусе
        Location targetLocation = findNearestPreyLocation();

        if (targetLocation != null) {
            // Двигаемся в направлении добычи
            moveTowardsPrey(targetLocation);
        } else {
            // Если добычи нет - двигаемся случайно
            moveRandomly();
        }
    }

    /**
     * Проверяет, есть ли добыча в текущей локации
     */
    private boolean hasPreyInCurrentLocation() {
        return currentLocation.getAliveAnimals().stream()
                .anyMatch(animal -> animal != this && animal.isAlive() && canEat(animal));
    }

    /**
     * Находит ближайшую локацию с добычей в увеличенном радиусе
     */
    private Location findNearestPreyLocation() {
        int currentX = currentLocation.getX();
        int currentY = currentLocation.getY();

        int searchRadius = maxSpeed * 3;

        for (int radius = 1; radius <= searchRadius; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    if (Math.abs(dx) == radius && Math.abs(dy) == radius) continue;
                    int targetX = currentX + dx;
                    int targetY = currentY + dy;
                    if (isValidLocation(targetX, targetY)) {
                        Location location = getLocation(targetX, targetY);
                        if (hasPreyInLocation(location)) {
                            log.trace("{} detected prey at [{},{}]", getClass().getSimpleName(), targetX, targetY);
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Проверяет, есть ли добыча в указанной локации
     */
    private boolean hasPreyInLocation(Location location) {
        if (location == null) return false;
        return location.getAliveAnimals().stream()
                .anyMatch(animal -> animal.isAlive() && canEat(animal));
    }

    /**
     * Двигается в направлении добычи
     */
    private void moveTowardsPrey(Location preyLocation) {
        if (preyLocation == null) return;

        int currentX = currentLocation.getX();
        int currentY = currentLocation.getY();
        int preyX = preyLocation.getX();
        int preyY = preyLocation.getY();

        // Вычисляем направление к добыче
        int directionX = Integer.compare(preyX, currentX);
        int directionY = Integer.compare(preyY, currentY);

        // Вычисляем новые координаты (двигаемся в направлении добычи)
        int newX = currentX + directionX * maxSpeed;
        int newY = currentY + directionY * maxSpeed;

        // Ограничиваем координаты границами острова
        newX = Math.max(0, Math.min(newX, IslandService.getWidth() - 1));
        newY = Math.max(0, Math.min(newY, IslandService.getHeight() - 1));

        // Получаем целевую локацию
        Location targetLocation = getLocation(newX, newY);

        if (targetLocation != null && targetLocation.canAddAnimal(this.getClass())) {
            // Перемещаем животное
            currentLocation.removeAnimal(this);
            targetLocation.addAnimal(this);
            currentLocation = targetLocation;

            log.debug("{} moved towards prey to [{},{}]",
                    getClass().getSimpleName(), newX, newY);
        }
    }

    /**
     * Случайное движение (запасной вариант)
     */
    private void moveRandomly() {
        int currentX = currentLocation.getX();
        int currentY = currentLocation.getY();

        // Генерируем случайное направление
        int dx = (int) (Math.random() * (maxSpeed * 2 + 1)) - maxSpeed;
        int dy = (int) (Math.random() * (maxSpeed * 2 + 1)) - maxSpeed;

        int newX = currentX + dx;
        int newY = currentY + dy;

        // Проверяем валидность новой локации
        if (isValidLocation(newX, newY)) {
            Location newLocation = getLocation(newX, newY);
            if (newLocation != null && newLocation.canAddAnimal(this.getClass())) {
                currentLocation.removeAnimal(this);
                newLocation.addAnimal(this);
                currentLocation = newLocation;

                log.trace("{} moved randomly to [{},{}]",
                        getClass().getSimpleName(), newX, newY);
            }
        }
    }

    @Override
    public void decreaseSatiety() {
        this.satiety = Math.max(0, this.satiety - (maxFoodRequired * 0.01));
        log.trace("{} didn't eat, satiety decreased to: {}/{}",
                this.getClass().getSimpleName(), this.satiety, this.maxFoodRequired);

        if (this.satiety <= 0) {
            this.alive = false;
            log.debug("{} died from hunger", this.getClass().getSimpleName());
        }
    }
}