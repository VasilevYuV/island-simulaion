package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.config.AnimalConfig;

public class Boa extends Predator {
    private static final AnimalConfig config = AnimalConfig.BOA;

    public Boa() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Boa(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        return animal instanceof Rabbit || animal instanceof Mouse || animal instanceof Duck;
    }

    @Override
    public double getEatingProbability(Animal animal) {
        if (animal instanceof Rabbit) return 70.0;
        if (animal instanceof Mouse) return 80.0;
        if (animal instanceof Duck) return 50.0;
        return 0.0;
    }
}