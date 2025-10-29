package com.VasilevYuV.island.animals.predators;

import com.VasilevYuV.island.animals.*;
import com.VasilevYuV.island.animals.herbivores.*;
import com.VasilevYuV.island.config.AnimalConfig;

public class Bear extends Predator {
    private static final AnimalConfig config = AnimalConfig.BEAR;

    public Bear() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Bear(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        // Медведь всеядный - ест почти всех кроме других медведей и орлов
        return !(animal instanceof Bear) && !(animal instanceof Eagle);
    }

    @Override
    public double getEatingProbability(Animal animal) {
        if (animal instanceof Boa) return 90.0;      // было 80.0
        if (animal instanceof Horse) return 60.0;    // было 40.0
        if (animal instanceof Deer) return 90.0;     // было 80.0
        if (animal instanceof Rabbit) return 95.0;   // было 80.0
        if (animal instanceof Mouse) return 98.0;    // было 90.0
        if (animal instanceof Goat) return 85.0;     // было 70.0
        if (animal instanceof Sheep) return 85.0;    // было 70.0
        if (animal instanceof Boar) return 70.0;     // было 50.0
        if (animal instanceof Buffalo) return 40.0;  // было 20.0
        if (animal instanceof Duck) return 30.0;     // было 10.0
        return 0.0;
    }
}