package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.config.AnimalConfig;

public class Horse extends Herbivore {
    private static final AnimalConfig config = AnimalConfig.HORSE;

    public Horse() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Horse(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        return false; // Лошадь не ест животных
    }

    @Override
    public double getEatingProbability(Animal animal) {
        return 0.0;
    }
}