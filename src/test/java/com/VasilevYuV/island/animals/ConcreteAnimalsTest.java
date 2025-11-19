package com.VasilevYuV.island.animals;

import com.VasilevYuV.island.animals.predators.*;
import com.VasilevYuV.island.animals.herbivores.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteAnimalsTest {

    @Test
    void testWolfCreation() {
        Wolf wolf = new Wolf();
        assertNotNull(wolf);
        assertTrue(wolf.isAlive());
        assertEquals("Wolf", wolf.getClass().getSimpleName());
        assertTrue(wolf.getWeight() > 0);
        assertTrue(wolf.getMaxSpeed() >= 0);
    }

    @Test
    void testRabbitCreation() {
        Rabbit rabbit = new Rabbit();
        assertNotNull(rabbit);
        assertTrue(rabbit.isAlive());
        assertEquals("Rabbit", rabbit.getClass().getSimpleName());
        assertTrue(rabbit.getWeight() > 0);
    }

    @Test
    void testBearCreation() {
        Bear bear = new Bear();
        assertNotNull(bear);
        assertTrue(bear.isAlive());
        assertEquals("Bear", bear.getClass().getSimpleName());
        assertTrue(bear.getWeight() > 0);
    }

    @Test
    void testAllPredatorsCreation() {
        // Тестируем создание всех хищников
        assertDoesNotThrow(() -> {
            new Wolf();
            new Bear();
            new Fox();
            new Eagle();
            new Boa();
        });
    }

    @Test
    void testAllHerbivoresCreation() {
        // Тестируем создание всех травоядных
        assertDoesNotThrow(() -> {
            new Horse();
            new Deer();
            new Rabbit();
            new Mouse();
            new Goat();
            new Sheep();
            new Boar();
            new Buffalo();
            new Duck();
            new Caterpillar();
        });
    }

    @Test
    void testPredatorCanEatLogic() {
        Wolf wolf = new Wolf();
        Rabbit rabbit = new Rabbit();
        Bear bear = new Bear();

        // Волк может есть травоядных
        assertTrue(wolf.canEat(rabbit));

        // Волк не может есть других хищников
        assertFalse(wolf.canEat(bear));
        assertFalse(wolf.canEat(new Wolf()));
    }

    @Test
    void testHerbivoreCannotEatAnimals() {
        Rabbit rabbit = new Rabbit();
        Mouse mouse = new Mouse();

        // Травоядные не могут есть других животных
        assertFalse(rabbit.canEat(mouse));
        assertEquals(0.0, rabbit.getEatingProbability(mouse));
    }
}