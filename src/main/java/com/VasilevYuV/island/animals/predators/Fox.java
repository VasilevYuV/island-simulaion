package com.VasilevYuV.island.animals.predators;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.animals.herbivores.Mouse;
import com.VasilevYuV.island.animals.Predator;
import com.VasilevYuV.island.animals.herbivores.Rabbit;
import com.VasilevYuV.island.animals.herbivores.Caterpillar;
import com.VasilevYuV.island.animals.herbivores.Duck;
import com.VasilevYuV.island.config.AnimalConfig;

public class Fox extends Predator {
    private static final AnimalConfig config = AnimalConfig.FOX;

    public Fox() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Fox(double weight, double maxFoodRequired, int maxSpeed) {
        super(weight, maxFoodRequired, maxSpeed);
    }

    @Override
    public boolean canEat(Animal animal) {
        return animal instanceof Rabbit || animal instanceof Mouse || animal instanceof Duck || animal instanceof Caterpillar;
    }

    @Override
    public double getEatingProbability(Animal animal) {
        if (animal instanceof Rabbit) return 90.0;
        if (animal instanceof Mouse) return 90.0;
        if (animal instanceof Duck) return 80.0;
        if (animal instanceof Caterpillar) return 70.0;
        return 0.0;
    }
}