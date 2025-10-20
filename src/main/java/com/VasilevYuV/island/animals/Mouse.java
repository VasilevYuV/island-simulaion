package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.config.AnimalConfig;

public class Mouse extends Herbivore {
    private static final AnimalConfig config = AnimalConfig.MOUSE;

    public Mouse() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Mouse(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        return animal instanceof Caterpillar;
    }

    @Override
    public double getEatingProbability(Animal animal) {
        if (animal instanceof Caterpillar) return 90.0;
        return 0.0;
    }
}