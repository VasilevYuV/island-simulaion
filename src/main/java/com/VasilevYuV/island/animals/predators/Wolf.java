package com.VasilevYuV.island.animals.predators;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.animals.Herbivore;
import com.VasilevYuV.island.animals.Predator;
import com.VasilevYuV.island.animals.herbivores.*;
import com.VasilevYuV.island.config.AnimalConfig;

public class Wolf extends Predator {
    private static final AnimalConfig config = AnimalConfig.WOLF;

    public Wolf() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Wolf(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        if (animal instanceof Wolf || animal instanceof Bear || animal instanceof Eagle) return false;
        return animal instanceof Herbivore;
    }

    @Override
    public double getEatingProbability(Animal animal) {
        if (animal instanceof Horse) return 40.0;    // было 10.0
        if (animal instanceof Deer) return 50.0;     // было 15.0
        if (animal instanceof Rabbit) return 80.0;   // было 60.0
        if (animal instanceof Mouse) return 90.0;    // было 80.0
        if (animal instanceof Goat) return 70.0;     // было 60.0
        if (animal instanceof Sheep) return 80.0;    // было 70.0
        if (animal instanceof Boar) return 40.0;     // было 15.0
        if (animal instanceof Buffalo) return 20.0;  // было 10.0
        if (animal instanceof Duck) return 80.0;     // было 40.0
        return 0.0;
    }
}