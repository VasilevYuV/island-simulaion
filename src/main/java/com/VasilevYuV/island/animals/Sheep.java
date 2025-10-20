package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.config.AnimalConfig;

public class Sheep extends Herbivore {
    private static final AnimalConfig config = AnimalConfig.SHEEP;

    public Sheep() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Sheep(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        return false;
    }

    @Override
    public double getEatingProbability(Animal animal) {
        return 0.0;
    }
}