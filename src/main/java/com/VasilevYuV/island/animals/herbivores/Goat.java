package com.VasilevYuV.island.animals.herbivores;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.animals.Herbivore;
import com.VasilevYuV.island.config.AnimalConfig;

public class Goat extends Herbivore {
    private static final AnimalConfig config = AnimalConfig.GOAT;

    public Goat() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Goat(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        return false;
    }

    @Override
    public double getEatingProbability(Animal animal) {
        return .0;
    }
}