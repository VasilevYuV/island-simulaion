package com.VasilevYuV.island.island;

import com.VasilevYuV.island.animals.herbivores.Rabbit;
import com.VasilevYuV.island.location.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    private Island island;

    @BeforeEach
    void setUp() {
        island = new Island();
        // Используем небольшой размер для тестов
        island.initialize(5, 5, 10);
    }

    @Test
    void testIslandInitialization() {
        assertEquals(5, island.getWidth());
        assertEquals(5, island.getHeight());
        assertTrue(island.isInitialized());
    }

    @Test
    void testGetLocation() {
        Location location = island.getLocation(2, 2);
        assertNotNull(location);
        assertEquals(2, location.getX());
        assertEquals(2, location.getY());
    }

    @Test
    void testGetInvalidLocation() {
        assertNull(island.getLocation(-1, -1));
        assertNull(island.getLocation(10, 10));
    }

    @Test
    void testAddAnimal() {
        Rabbit rabbit = new Rabbit();
        island.addAnimal(rabbit, 1, 1);

        Location location = island.getLocation(1, 1);
        assertTrue(location.getAnimals().contains(rabbit));
    }

    @Test
    void testRemoveDeadAnimals() {
        Rabbit rabbit = new Rabbit();
        rabbit.setAlive(false);

        Location location = island.getLocation(0, 0);
        location.addAnimal(rabbit);

        island.removeDeadAnimals();

        assertFalse(location.getAnimals().contains(rabbit));
    }

    @Test
    void testTotalAnimalsCount() {
        Location loc1 = island.getLocation(0, 0);
        Location loc2 = island.getLocation(1, 1);

        loc1.addAnimal(new Rabbit());
        loc2.addAnimal(new Rabbit());

        assertEquals(2, island.getTotalAnimals());
    }

    @Test
    void testTotalPlantsCount() {
        assertTrue(island.getTotalPlants() >= 0);
    }

    @Test
    void testTurnIncrement() {
        int initialTurn = island.getCurrentTurn();
        island.incrementTurn();
        assertEquals(initialTurn + 1, island.getCurrentTurn());
    }

    @Test
    void testIsValidLocation() {
        assertTrue(island.isValidLocation(0, 0));
        assertTrue(island.isValidLocation(4, 4));
        assertFalse(island.isValidLocation(-1, 0));
        assertFalse(island.isValidLocation(0, 5));
        assertFalse(island.isValidLocation(5, 0));
    }
}