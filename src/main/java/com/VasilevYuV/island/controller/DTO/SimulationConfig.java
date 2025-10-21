package com.VasilevYuV.island.controller.DTO;

public class SimulationConfig {
    private Integer width = 100;
    private Integer height = 20;
    private int turnDurationMs = 1000;

    // Хищники
    private int initialWolves = 20;
    private int initialBoas = 10;
    private int initialFoxes = 16;
    private int initialBears = 8;
    private int initialEagles = 12;

    // Травоядные
    private int initialHorses = 50;
    private int initialDeer = 50;
    private int initialRabbits = 50;
    private int initialMice = 50;
    private int initialGoats = 20;
    private int initialSheep = 18;
    private int initialBoars = 10;
    private int initialBuffalo = 5;
    private int initialDucks = 30;
    private int initialCaterpillars = 200;

    private int initialPlants = 50;

    // Getters and Setters для всех полей
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getTurnDurationMs() { return turnDurationMs; }
    public void setTurnDurationMs(int turnDurationMs) { this.turnDurationMs = turnDurationMs; }

    // Хищники
    public int getInitialWolves() { return initialWolves; }
    public void setInitialWolves(int initialWolves) { this.initialWolves = initialWolves; }

    public int getInitialBoas() { return initialBoas; }
    public void setInitialBoas(int initialBoas) { this.initialBoas = initialBoas; }

    public int getInitialFoxes() { return initialFoxes; }
    public void setInitialFoxes(int initialFoxes) { this.initialFoxes = initialFoxes; }

    public int getInitialBears() { return initialBears; }
    public void setInitialBears(int initialBears) { this.initialBears = initialBears; }

    public int getInitialEagles() { return initialEagles; }
    public void setInitialEagles(int initialEagles) { this.initialEagles = initialEagles; }

    // Травоядные
    public int getInitialHorses() { return initialHorses; }
    public void setInitialHorses(int initialHorses) { this.initialHorses = initialHorses; }

    public int getInitialDeer() { return initialDeer; }
    public void setInitialDeer(int initialDeer) { this.initialDeer = initialDeer; }

    public int getInitialRabbits() { return initialRabbits; }
    public void setInitialRabbits(int initialRabbits) { this.initialRabbits = initialRabbits; }

    public int getInitialMice() { return initialMice; }
    public void setInitialMice(int initialMice) { this.initialMice = initialMice; }

    public int getInitialGoats() { return initialGoats; }
    public void setInitialGoats(int initialGoats) { this.initialGoats = initialGoats; }

    public int getInitialSheep() { return initialSheep; }
    public void setInitialSheep(int initialSheep) { this.initialSheep = initialSheep; }

    public int getInitialBoars() { return initialBoars; }
    public void setInitialBoars(int initialBoars) { this.initialBoars = initialBoars; }

    public int getInitialBuffalo() { return initialBuffalo; }
    public void setInitialBuffalo(int initialBuffalo) { this.initialBuffalo = initialBuffalo; }

    public int getInitialDucks() { return initialDucks; }
    public void setInitialDucks(int initialDucks) { this.initialDucks = initialDucks; }

    public int getInitialCaterpillars() { return initialCaterpillars; }
    public void setInitialCaterpillars(int initialCaterpillars) { this.initialCaterpillars = initialCaterpillars; }

    public int getInitialPlants() { return initialPlants; }
    public void setInitialPlants(int initialPlants) { this.initialPlants = initialPlants; }

    public boolean isValid() {
        return width >= 50 && width <= 200 &&
                height >= 10 && height <= 50 &&
                turnDurationMs >= 100 && turnDurationMs <= 5000;
    }

    @Override
    public String toString() {
        return "SimulationConfig{" +
                "width=" + width +
                ", height=" + height +
                ", turnDurationMs=" + turnDurationMs +
                // Хищники
                ", wolves=" + initialWolves +
                ", boas=" + initialBoas +
                ", foxes=" + initialFoxes +
                ", bears=" + initialBears +
                ", eagles=" + initialEagles +
                // Травоядные
                ", horses=" + initialHorses +
                ", deer=" + initialDeer +
                ", rabbits=" + initialRabbits +
                ", mice=" + initialMice +
                ", goats=" + initialGoats +
                ", sheep=" + initialSheep +
                ", boars=" + initialBoars +
                ", buffalo=" + initialBuffalo +
                ", ducks=" + initialDucks +
                ", caterpillars=" + initialCaterpillars +
                // Растения
                ", plants=" + initialPlants +
                '}';
    }
}