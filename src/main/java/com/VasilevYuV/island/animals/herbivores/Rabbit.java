package com.VasilevYuV.island.animals.herbivores;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.animals.Herbivore;
import com.VasilevYuV.island.config.AnimalConfig;

public class Rabbit extends Herbivore {
    private static final AnimalConfig config = AnimalConfig.RABBIT;

    public Rabbit() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Rabbit(double weight, double maxFoodRequired, int maxSpeed) {
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