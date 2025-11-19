package com.VasilevYuV.island.service;

import com.VasilevYuV.island.config.SimulationProperties;
import com.VasilevYuV.island.controller.DTO.SimulationConfig;
import com.VasilevYuV.island.event.SpeedChangeEvent;
import com.VasilevYuV.island.island.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulationEngineTest {
    private StatisticsService statisticsService;
    private SimulationProperties properties;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private Island island;
    private SimulationEngine simulationEngine;
    private SimulationConfig config;

    @BeforeEach
    void setUp() {
        island = new Island();
        properties = new SimulationProperties();
        simulationEngine = new SimulationEngine(
                island, statisticsService, properties, eventPublisher
        );

        config = new SimulationConfig();
    }

    @Test
    void testIslandInitializationWithConfig() {
        // When
        simulationEngine.initializeIsland(config.getWidth(), config.getHeight(), config);

        // Then
        assertTrue(island.isInitialized());
        assertEquals(config.getWidth(), island.getWidth());
        assertEquals(config.getHeight(), island.getHeight());
        assertTrue(island.getTotalAnimals() > 0);
    }

    @Test
    void testIslandInitializationWithCustomSize() {
        // Given
        int customWidth = 50;
        int customHeight = 30;

        // When
        simulationEngine.initializeIsland(customWidth, customHeight, config);

        // Then
        assertEquals(customWidth, island.getWidth());
        assertEquals(customHeight, island.getHeight());
        assertTrue(island.isInitialized());
    }

    @Test
    void testSimulationLifecycle() {
        // Given
        simulationEngine.initializeIsland(config.getWidth(), config.getHeight(), config);

        // When & Then
        assertFalse(simulationEngine.isRunning());

        simulationEngine.startSimulation();
        assertTrue(simulationEngine.isRunning());

        simulationEngine.pauseSimulation();
        assertTrue(simulationEngine.isPaused());

        simulationEngine.resumeSimulation();
        assertFalse(simulationEngine.isPaused());

        simulationEngine.stopSimulation();
        assertFalse(simulationEngine.isRunning());
    }

    @Test
    void testTurnDurationChange() {
        // When
        simulationEngine.setTurnDuration(500);

        // Then
        assertEquals(500, simulationEngine.getTurnDurationMs());
        verify(eventPublisher).publishEvent(any(SpeedChangeEvent.class));
    }

    @Test
    void testRunTurnExecutesSuccessfully() {
        // Given
        simulationEngine.initializeIsland(config.getWidth(), config.getHeight(), config);

        // When
        simulationEngine.runTurn();

        // Then - не должно быть исключений
        assertTrue(true);
    }

    @Test
    void testStatisticsCollection() {
        // Given
        simulationEngine.initializeIsland(config.getWidth(), config.getHeight(), config);

        // When
        simulationEngine.runTurn();

        // Then
        assertNotNull(simulationEngine.getStatistics());
        assertTrue(simulationEngine.getStatistics().containsKey("totalAnimals"));
        assertTrue(simulationEngine.getStatistics().containsKey("totalPlants"));
        assertTrue(simulationEngine.getStatistics().containsKey("currentTurn"));
    }

    @Test
    void testStopConditionsWithNoAnimals() {
        // Given
        SimulationConfig emptyConfig = new SimulationConfig() {
            @Override public int getInitialWolves() { return 0; }
            @Override public int getInitialBoas() { return 0; }
            @Override public int getInitialFoxes() { return 0; }
            @Override public int getInitialBears() { return 0; }
            @Override public int getInitialEagles() { return 0; }
            @Override public int getInitialHorses() { return 0; }
            @Override public int getInitialDeer() { return 0; }
            @Override public int getInitialRabbits() { return 0; }
            @Override public int getInitialMice() { return 0; }
            @Override public int getInitialGoats() { return 0; }
            @Override public int getInitialSheep() { return 0; }
            @Override public int getInitialBoars() { return 0; }
            @Override public int getInitialBuffalo() { return 0; }
            @Override public int getInitialDucks() { return 0; }
            @Override public int getInitialCaterpillars() { return 0; }
        };

        simulationEngine.initializeIsland(10, 10, emptyConfig);

        // When
        simulationEngine.runTurn();

        // Then - не должно упасть
        assertFalse(simulationEngine.isRunning());
    }

    @Test
    void testAnimalCreationFromConfig() {
        // Given
        simulationEngine.initializeIsland(config.getWidth(), config.getHeight(), config);

        // When
        int totalAnimals = island.getTotalAnimals();

        // Then
        assertTrue(totalAnimals > 0, "Should create animals from config");

        // Проверяем что создались животные согласно конфигу
        int expectedMinAnimals = config.getInitialWolves() + config.getInitialBoas() +
                config.getInitialFoxes() + config.getInitialBears() +
                config.getInitialEagles() + config.getInitialHorses() +
                config.getInitialDeer() + config.getInitialRabbits() +
                config.getInitialMice() + config.getInitialGoats() +
                config.getInitialSheep() + config.getInitialBoars() +
                config.getInitialBuffalo() + config.getInitialDucks() +
                config.getInitialCaterpillars();

        assertTrue(totalAnimals >= expectedMinAnimals,
                "Should create at least the number of animals specified in config");
    }

    @Test
    void testPlantInitializationFromConfig() {
        // Given
        simulationEngine.initializeIsland(config.getWidth(), config.getHeight(), config);

        // When
        int totalPlants = island.getTotalPlants();

        // Then
        assertTrue(totalPlants >= config.getInitialPlants(),
                "Should initialize at least the number of plants specified in config");
    }

    @Test
    void testMultipleTurnsExecution() {
        // Given
        simulationEngine.initializeIsland(10, 10, config);

        // When
        for (int i = 0; i < 3; i++) {
            simulationEngine.runTurn();
        }

        // Then - не должно быть исключений после нескольких ходов
        assertTrue(true);
    }

    @Test
    void testCleanup() {
        // Given
        simulationEngine.startSimulation();

        // When
        simulationEngine.cleanup();

        // Then - не должно быть исключений при cleanup
        assertTrue(true);
    }

    @Test
    void testGetStatisticsBeforeInitialization() {
        // When
        var statistics = simulationEngine.getStatistics();

        // Then
        assertNotNull(statistics);
        assertTrue(statistics.containsKey("currentTurn"));
    }

    public void setStatisticsService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    public void setProperties(SimulationProperties properties) {
        this.properties = properties;
    }

    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}