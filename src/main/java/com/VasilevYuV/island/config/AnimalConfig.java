package com.VasilevYuV.island.config;

public enum AnimalConfig {
    // Хищники
    WOLF(50, 12, 3, 0.7, 0.8),
    BOA(15, 5, 1, 0.5, 0.6),
    FOX(8, 6, 2, 0.7, 0.75),
    BEAR(500, 80, 2, 0.4, 0.5),
    EAGLE(6, 6, 3, 0.8, 0.9),

    // Травоядные
    HORSE(400, 60, 4, 0.6, 0.7),
    DEER(300, 50, 4, 0.7, 0.8),
    RABBIT(3, 0.45, 2, 0.9, 0.6),
    MOUSE(2, 0.01, 1, 0.8, 0.5),
    GOAT(60, 10, 3, 0.6, 0.7),
    SHEEP(70, 15, 3, 0.5, 0.6),
    BOAR(400, 50, 2, 0.5, 0.7),
    BUFFALO(700, 100, 3, 0.4, 0.7),
    DUCK(5, 0.45, 4, 0.8, 0.6),
    CATERPILLAR(0.01, 0, 0, 0.0, 0.0); // Гусеница не двигается

    private final double weight;
    private final double maxFoodRequired;
    private final int maxSpeed;
    private final double moveProbability;
    private final double reproductionProbability;

    AnimalConfig(double weight, double maxFoodRequired, int maxSpeed,
                 double moveProbability, double reproductionProbability) {
        this.weight = weight;
        this.maxFoodRequired = maxFoodRequired;
        this.maxSpeed = maxSpeed;
        this.moveProbability = moveProbability;
        this.reproductionProbability = reproductionProbability;
    }

    // Getters
    public double getWeight() { return weight; }
    public double getMaxFoodRequired() { return maxFoodRequired; }
    public int getMaxSpeed() { return maxSpeed; }
    public double getMoveProbability() { return moveProbability; }
    public double getReproductionProbability() { return reproductionProbability; }

    public int getMaxPerLocation() {
        switch (this) {
            case WOLF: return 10;
            case BEAR: return 4;
            case FOX: return 10;
            case EAGLE: return 18;
            case BOA, GOAT: return 12;
            case HORSE: return 10;
            case DEER: return 10;
            case RABBIT, DUCK, MOUSE: return 20;
            case SHEEP: return 15;
            case BOAR: return 10;
            case BUFFALO: return 5;
            case CATERPILLAR: return 100;
            default: return 10;
        }
    }
}