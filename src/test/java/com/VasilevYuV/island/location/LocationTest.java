package com.VasilevYuV.island.location;

import com.VasilevYuV.island.animals.herbivores.Rabbit;
import com.VasilevYuV.island.animals.predators.Wolf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    private Location location;
    private Rabbit rabbit;
    private Wolf wolf;

    @BeforeEach
    void setUp() {
        location = new Location(5, 10, 10);
        rabbit = new Rabbit();
        wolf = new Wolf();
    }

    @Test
    void testLocationCreation() {
        assertEquals(5, location.getX());
        assertEquals(10, location.getY());
        assertEquals(10, location.getPlantCount());
    }

    @Test
    void testAddAnimal() {
        location.addAnimal(rabbit);

        assertEquals(1, location.getAnimalCount());
        assertEquals(location, rabbit.getCurrentLocation());
    }

    @Test
    void testRemoveAnimal() {
        location.addAnimal(rabbit);
        location.removeAnimal(rabbit);

        assertEquals(0, location.getAnimalCount());
    }

    @Test
    void testGetAliveAnimals() {
        location.addAnimal(rabbit);
        wolf.setAlive(false);
        location.addAnimal(wolf);

        assertEquals(1, location.getAliveAnimals().size());
        assertTrue(location.getAliveAnimals().contains(rabbit));
    }

    @Test
    void testPlantGrowth() {
        int initialPlants = location.getPlantCount();
        location.growPlants();
        assertTrue(location.getPlantCount() > initialPlants);
    }

    @Test
    void testPlantConsumption() {
        location.consumePlants(2);
        assertEquals(8, location.getPlantCount());
    }

    @Test
    void testCannotConsumeMoreThanAvailable() {
        location.consumePlants(25); // Пытаемся съесть больше чем есть
        assertEquals(0, location.getPlantCount());
    }

    @Test
    void testCanAddAnimal() {
        assertTrue(location.canAddAnimal(Rabbit.class));
    }

    @Test
    void testGetAnimalsCount() {
        location.addAnimal(rabbit);
        location.addAnimal(new Rabbit());

        assertEquals(2, location.getAnimalsCount(Rabbit.class));
    }

    @Test
    void testLockUnlock() {
        assertTrue(location.tryLock());
        location.unlock();

        // Должен успешно захватить лок после разблокировки
        assertTrue(location.tryLock());
        location.unlock();
    }
}