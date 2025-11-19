package com.VasilevYuV.island.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalConfigTest {

    @Test
    void testAnimalConfigValues() {
        // Проверяем что все значения корректны
        assertEquals(50.0, AnimalConfig.WOLF.getWeight());
        assertEquals(12.0, AnimalConfig.WOLF.getMaxFoodRequired());
        assertEquals(3, AnimalConfig.WOLF.getMaxSpeed());
        assertEquals(0.7, AnimalConfig.WOLF.getMoveProbability());
        assertEquals(0.8, AnimalConfig.WOLF.getReproductionProbability());
    }

    @Test
    void testAllConfigsHaveValidValues() {
        for (AnimalConfig config : AnimalConfig.values()) {
            assertTrue(config.getWeight() > 0,
                    config.name() + " weight should be positive");
            assertTrue(config.getMaxFoodRequired() >= 0,
                    config.name() + " max food should be non-negative");
            assertTrue(config.getMaxSpeed() >= 0,
                    config.name() + " max speed should be non-negative");
            assertTrue(config.getMoveProbability() >= 0 && config.getMoveProbability() <= 1,
                    config.name() + " move probability should be between 0 and 1");
            assertTrue(config.getReproductionProbability() >= 0 && config.getReproductionProbability() <= 1,
                    config.name() + " reproduction probability should be between 0 and 1");
        }
    }

    @Test
    void testMaxPerLocation() {
        // Проверяем что максимальное количество животных в локации положительное
        assertTrue(AnimalConfig.WOLF.getMaxPerLocation() > 0);
        assertTrue(AnimalConfig.RABBIT.getMaxPerLocation() > 0);
        assertTrue(AnimalConfig.CATERPILLAR.getMaxPerLocation() > 0);

        // Проверяем конкретные значения
        assertEquals(10, AnimalConfig.WOLF.getMaxPerLocation());
        assertEquals(20, AnimalConfig.RABBIT.getMaxPerLocation());
        assertEquals(100, AnimalConfig.CATERPILLAR.getMaxPerLocation());
    }
}