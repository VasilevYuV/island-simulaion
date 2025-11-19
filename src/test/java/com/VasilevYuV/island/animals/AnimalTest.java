package com.VasilevYuV.island.animals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    private Animal testAnimal;

    @BeforeEach
    void setUp() {
        testAnimal = new Animal(10.0, 5.0, 2) {
            @Override
            public void eat() {
                this.setSatiety(this.getSatiety() + 1.0);
            }

            @Override
            public boolean canEat(Animal animal) {
                return false;
            }

            @Override
            public double getEatingProbability(Animal animal) {
                return 0.0;
            }
        };
    }

    @Test
    void testAnimalCreation() {
        assertNotNull(testAnimal.getId());
        assertEquals(10.0, testAnimal.getWeight());
        assertEquals(5.0, testAnimal.getMaxFoodRequired());
        assertEquals(2, testAnimal.getMaxSpeed());
        assertTrue(testAnimal.isAlive());
        assertTrue(testAnimal.getSatiety() > 0);
    }

    @Test
    void testDecreaseSatiety() {
        double initialSatiety = testAnimal.getSatiety();
        testAnimal.decreaseSatiety();
        assertTrue(testAnimal.getSatiety() < initialSatiety);
    }

    @Test
    void testDeathFromHunger() {
        testAnimal.setSatiety(0.1);
        testAnimal.decreaseSatiety();
        assertFalse(testAnimal.isAlive());
    }

    @Test
    void testEatMethod() {
        double initialSatiety = testAnimal.getSatiety();
        testAnimal.eat();
        assertTrue(testAnimal.getSatiety() > initialSatiety);
    }

    @Test
    void testAnimalStaysAliveWithSatiety() {
        testAnimal.setSatiety(1.0);
        testAnimal.decreaseSatiety();
        assertTrue(testAnimal.isAlive());
    }
}