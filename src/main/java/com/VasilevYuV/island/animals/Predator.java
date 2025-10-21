package com.VasilevYuV.island.animals;

import java.util.List;

public abstract class Predator extends Animal {
    public Predator(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public void eat() {
        if (currentLocation == null || !isAlive()) return;

        boolean ateSomething = false;

        // Ищем ВСЮ доступную добычу
        List<Animal> potentialPrey = currentLocation.getAliveAnimals().stream()
                .filter(animal -> animal != this && animal.isAlive() && canEat(animal))
                .toList();

        // Пробуем съесть каждую возможную добычу
        for (Animal prey : potentialPrey) {
            double probability = getEatingProbability(prey);
            if (Math.random() * 100 < probability) {
                // Успешная охота
                prey.setAlive(false);
                double nutrition = prey.getWeight() * 0.8; // 80% веса добычи идет в сытость
                this.satiety = Math.min(this.maxFoodRequired, this.satiety + nutrition);
                currentLocation.removeAnimal(prey);
                log.debug("{} ate {} and gained {} nutrition, satiety: {}/{}",
                        this.getClass().getSimpleName(),
                        prey.getClass().getSimpleName(),
                        nutrition,
                        this.satiety, this.maxFoodRequired);
                ateSomething = true;
                break; // Хищник насытился после одной успешной охоты
            }
        }

        // Если хищник всеядный (медведь), может есть растения
        if (!ateSomething && this instanceof Bear && currentLocation.getPlantCount() > 0) {
            int plantsToEat = Math.min(10, currentLocation.getPlantCount());
            currentLocation.consumePlants(plantsToEat);
            double plantNutrition = plantsToEat * 0.5; // Увеличиваем питательность растений для медведя
            this.satiety = Math.min(this.maxFoodRequired, this.satiety + plantNutrition);
            ateSomething = true;
            log.debug("Bear ate {} plants, gained {} nutrition", plantsToEat, plantNutrition);
        }

        // Уменьшаем сытость ТОЛЬКО если НЕ поели
        if (!ateSomething) {
            this.satiety = Math.max(0, this.satiety - (maxFoodRequired * 0.1)); // 10% потеря при голодании
            log.debug("{} didn't eat, satiety decreased to: {}/{}",
                    this.getClass().getSimpleName(), this.satiety, this.maxFoodRequired);

            if (this.satiety <= 0) {
                this.alive = false;
                log.debug("{} died from hunger", this.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void decreaseSatiety() {
        // Упрощаем - теперь основная логика в методе eat()
        this.satiety = Math.max(0, this.satiety - (maxFoodRequired * 0.05));
        if (this.satiety <= 0) {
            this.alive = false;
        }
    }
}