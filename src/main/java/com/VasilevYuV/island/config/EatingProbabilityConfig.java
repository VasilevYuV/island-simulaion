package com.VasilevYuV.island.config;

import java.util.HashMap;
import java.util.Map;

public class EatingProbabilityConfig {
    private final Map<String, Map<String, Double>> probabilities;

    public EatingProbabilityConfig() {
        this.probabilities = new HashMap<>();
        initializeProbabilities();
    }

    public Map<String, Map<String, Double>> getProbabilities() {
        return probabilities;
    }

    private void initializeProbabilities() {
        // Волк
        Map<String, Double> wolfProbabilities = new HashMap<>();
        wolfProbabilities.put("horse", 10.0);
        wolfProbabilities.put("deer", 15.0);
        wolfProbabilities.put("rabbit", 60.0);
        wolfProbabilities.put("mouse", 80.0);
        wolfProbabilities.put("goat", 60.0);
        wolfProbabilities.put("sheep", 70.0);
        wolfProbabilities.put("boar", 15.0);
        wolfProbabilities.put("buffalo", 10.0);
        wolfProbabilities.put("duck", 40.0);
        probabilities.put("wolf", wolfProbabilities);

        // Удав
        Map<String, Double> boaProbabilities = new HashMap<>();
        boaProbabilities.put("rabbit", 20.0);
        boaProbabilities.put("mouse", 40.0);
        boaProbabilities.put("duck", 10.0);
        probabilities.put("boa", boaProbabilities);

        // Лиса
        Map<String, Double> foxProbabilities = new HashMap<>();
        foxProbabilities.put("rabbit", 70.0);
        foxProbabilities.put("mouse", 90.0);
        foxProbabilities.put("duck", 60.0);
        foxProbabilities.put("caterpillar", 40.0);
        probabilities.put("fox", foxProbabilities);

        // Медведь
        Map<String, Double> bearProbabilities = new HashMap<>();
        bearProbabilities.put("boa", 80.0);
        bearProbabilities.put("rabbit", 80.0);
        bearProbabilities.put("mouse", 90.0);
        bearProbabilities.put("deer", 80.0);
        bearProbabilities.put("horse", 40.0);
        bearProbabilities.put("boar", 50.0);
        bearProbabilities.put("duck", 10.0);
        probabilities.put("bear", bearProbabilities);

        // Орел
        Map<String, Double> eagleProbabilities = new HashMap<>();
        eagleProbabilities.put("fox", 10.0);
        eagleProbabilities.put("rabbit", 90.0);
        eagleProbabilities.put("mouse", 90.0);
        eagleProbabilities.put("duck", 80.0);
        probabilities.put("eagle", eagleProbabilities);

        // Мышь (ест гусениц)
        Map<String, Double> mouseProbabilities = new HashMap<>();
        mouseProbabilities.put("caterpillar", 90.0);
        probabilities.put("mouse", mouseProbabilities);

        // Кабан (ест мышей и гусениц)
        Map<String, Double> boarProbabilities = new HashMap<>();
        boarProbabilities.put("mouse", 50.0);
        boarProbabilities.put("caterpillar", 90.0);
        probabilities.put("boar", boarProbabilities);

        // Утка (ест гусениц)
        Map<String, Double> duckProbabilities = new HashMap<>();
        duckProbabilities.put("caterpillar", 90.0);
        probabilities.put("duck", duckProbabilities);
    }

    public Double getProbability(String predatorType, String preyType) {
        Map<String, Double> predatorProbs = probabilities.get(predatorType);
        if (predatorProbs != null) {
            return predatorProbs.getOrDefault(preyType, 0.0);
        }
        return 0.0;
    }
}