package com.VasilevYuV.island.animals.predators;

import com.VasilevYuV.island.animals.*;
import com.VasilevYuV.island.animals.herbivores.Duck;
import com.VasilevYuV.island.animals.herbivores.Mouse;
import com.VasilevYuV.island.animals.herbivores.Rabbit;
import com.VasilevYuV.island.config.AnimalConfig;

public class Eagle extends Predator {
    private static final AnimalConfig config = AnimalConfig.EAGLE;

    public Eagle() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Eagle(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        return animal instanceof Fox || animal instanceof Rabbit || animal instanceof Mouse || animal instanceof Duck;
    }

    @Override
    public double getEatingProbability(Animal animal) {
        if (animal instanceof Fox) return 10.0;
        if (animal instanceof Rabbit) return 90.0;
        if (animal instanceof Mouse) return 90.0;
        if (animal instanceof Duck) return 80.0;
        return 0.0;
    }
}