package com.VasilevYuV.island.config;

public enum AnimalConfig {
    // Хищники
    WOLF(50, 8, 3, 0.7, 0.8),
    BOA(15, 3, 1, 0.5, 0.6),
    FOX(8, 2, 2, 0.7, 0.75),
    BEAR(500, 80, 2, 0.4, 0.5),
    EAGLE(6, 1, 3, 0.8, 0.9),

    // Травоядные
    HORSE(400, 60, 4, 0.6, 0.7),
    DEER(300, 50, 4, 0.7, 0.8),
    RABBIT(2, 0.45, 2, 0.9, 0.95),
    MOUSE(0.05, 0.01, 1, 0.8, 0.85),
    GOAT(60, 10, 3, 0.6, 0.7),
    SHEEP(70, 15, 3, 0.5, 0.6),
    BOAR(400, 50, 2, 0.5, 0.6),
    BUFFALO(700, 100, 3, 0.4, 0.5),
    DUCK(1, 0.15, 4, 0.8, 0.9),
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

    // Геттеры
    public double getWeight() { return weight; }
    public double getMaxFoodRequired() { return maxFoodRequired; }
    public int getMaxSpeed() { return maxSpeed; }
    public double getMoveProbability() { return moveProbability; }
    public double getReproductionProbability() { return reproductionProbability; }
}