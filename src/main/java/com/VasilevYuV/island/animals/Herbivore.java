package com.VasilevYuV.island.animals;

public abstract class Herbivore extends Animal {
    public Herbivore(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public void eat() {
        if (currentLocation == null || !isAlive()) return;
        int availablePlants = currentLocation.getPlantCount();

        if (availablePlants > 0) {
            // УВЕЛИЧИВАЕМ питательность растений для крупных животных
            double plantNutrition = calculatePlantNutrition();

            double satietyDeficit = this.maxFoodRequired - this.satiety;
            int plantsNeeded = (int) Math.ceil(satietyDeficit / plantNutrition);

            // УВЕЛИЧИВАЕМ лимит растений за прием пищи для крупных животных
            int maxPlantsPerMeal = calculateMaxPlantsPerMeal();
            int plantsToEat = Math.min(availablePlants, Math.min(plantsNeeded, maxPlantsPerMeal));

            if (plantsToEat > 0) {
                currentLocation.consumePlants(plantsToEat);
                double nutritionGained = plantsToEat * plantNutrition;
                this.satiety = Math.min(this.maxFoodRequired, this.satiety + nutritionGained);

                log.debug("{} ate {} plants, gained {} nutrition, satiety: {}/{}",
                        getClass().getSimpleName(), plantsToEat, nutritionGained,
                        this.satiety, this.maxFoodRequired);
            }
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

    private double calculatePlantNutrition() {
        // Крупные животные получают больше питания от растений
        double baseNutrition = 1.0;
        if (this.weight > 100) {
            return baseNutrition + (this.weight * 0.01); // +1% от веса
        }
        return baseNutrition;
    }

    private int calculateMaxPlantsPerMeal() {
        // Крупные животные могут есть больше растений за раз
        int baseLimit = (int) Math.ceil(this.maxFoodRequired * 0.3);
        if (this.weight > 100) {
            return baseLimit * 2; // Удваиваем лимит для крупных
        }
        return baseLimit;
    }

    protected boolean canEatOtherAnimals() {
        return this instanceof Mouse || this instanceof Boar || this instanceof Duck;
    }
}