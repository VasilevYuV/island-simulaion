package com.VasilevYuV.island.animals.herbivores;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.animals.Herbivore;
import com.VasilevYuV.island.config.AnimalConfig;

public class Caterpillar extends Herbivore {
    private static final AnimalConfig config = AnimalConfig.CATERPILLAR;

    public Caterpillar() {
        super(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
    }

    public Caterpillar(double weight, double maxFoodRequired, int maxSpeed) {
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

    @Override
    public void decreaseSatiety() {
        // Гусеница не теряет сытость (по таблице - 0 кг пищи нужно)
    }
}