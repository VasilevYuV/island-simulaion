package com.VasilevYuV.island.animals;

public abstract class Herbivore extends Animal {
    public Herbivore(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public void eat() {
        if (currentLocation == null || !isAlive()) return;

        // Поедание растений
        if (currentLocation.getPlantCount() > 0) {
            int plantsToEat = Math.min(3, currentLocation.getPlantCount());
            currentLocation.consumePlants(plantsToEat);
            this.satiety = Math.min(this.maxFoodRequired, this.satiety + (plantsToEat * 0.1));
        }

        // Некоторые травоядные едят гусениц
        if (this.canEatOtherAnimals()) {
            currentLocation.getAnimals().stream()
                    .filter(animal -> animal instanceof Caterpillar && animal.isAlive() && canEat(animal))
                    .findFirst()
                    .ifPresent(caterpillar -> {
                        double probability = getEatingProbability(caterpillar);
                        if (Math.random() * 100 < probability) {
                            caterpillar.setAlive(false);
                            this.satiety = Math.min(this.maxFoodRequired, this.satiety + caterpillar.getWeight());
                            currentLocation.removeAnimal(caterpillar);
                        }
                    });
        }

        decreaseSatiety();
    }

    protected boolean canEatOtherAnimals() {
        return this instanceof Mouse || this instanceof Boar || this instanceof Duck;
    }
}