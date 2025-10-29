package com.VasilevYuV.island.animals.herbivores;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.animals.Herbivore;
import com.VasilevYuV.island.config.AnimalConfig;

public class Buffalo extends Herbivore {
    private static final AnimalConfig config = AnimalConfig.BUFFALO;

    public Buffalo() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Buffalo(double weight, double maxFoodRequired, int maxSpeed) {
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