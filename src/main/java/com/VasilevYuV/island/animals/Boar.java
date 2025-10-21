package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.config.AnimalConfig;

public class Boar extends Herbivore {
    private static final AnimalConfig config = AnimalConfig.BOAR;

    public Boar() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Boar(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        return animal instanceof Mouse || animal instanceof Caterpillar;
    }

    @Override
    public double getEatingProbability(Animal animal) {
        if (animal instanceof Mouse) return 90.0;
        if (animal instanceof Caterpillar) return 90.0;
        return 0.0;
    }
}