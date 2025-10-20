package com.VasilevYuV.island.service;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.animals.*;
import com.VasilevYuV.island.config.AnimalConfig;
import com.VasilevYuV.island.config.SimulationProperties;
import com.VasilevYuV.island.controller.DTO.SimulationConfig;
import com.VasilevYuV.island.event.SpeedChangeEvent;
import com.VasilevYuV.island.island.Island;
import com.VasilevYuV.island.location.Location;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class SimulationEngine {
    private static final Logger log = LoggerFactory.getLogger(SimulationEngine.class);

    private final Island island;
    private final StatisticsService statisticsService;
    private final SimulationProperties properties;
    private final ApplicationEventPublisher eventPublisher;

    private volatile boolean running = false;
    private volatile boolean paused = false;

    private final ExecutorService locationExecutor;
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    private int turnDurationMs = 1000;

    public SimulationEngine(Island island,
                            StatisticsService statisticsService,
                            SimulationProperties properties,
                            ApplicationEventPublisher eventPublisher) {
        this.island = island;
        this.statisticsService = statisticsService;
        this.properties = properties;
        this.eventPublisher = eventPublisher;
        this.locationExecutor = Executors.newVirtualThreadPerTaskExecutor();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.turnDurationMs = properties.getTurnDurationMs();
    }

    public boolean isRunning() {
        return running;
    }

    public void initializeIsland(int width, int height, SimulationConfig config) {
        island.initialize(width, height);
        initializeAnimals(config);
    }

    private void initializeAnimals(SimulationConfig config) {
        log.info("Initializing animals on the island with config: {}", config);

        createAnimals(Wolf.class, config.getInitialWolves());
        createAnimals(Boa.class, config.getInitialBoas());
        createAnimals(Fox.class, config.getInitialFoxes());
        createAnimals(Bear.class, config.getInitialBears());
        createAnimals(Eagle.class, config.getInitialEagles());

        createAnimals(Horse.class, config.getInitialHorses());
        createAnimals(Deer.class, config.getInitialDeer());
        createAnimals(Rabbit.class, config.getInitialRabbits());
        createAnimals(Mouse.class, config.getInitialMice());
        createAnimals(Goat.class, config.getInitialGoats());
        createAnimals(Sheep.class, config.getInitialSheep());
        createAnimals(Boar.class, config.getInitialBoars());
        createAnimals(Buffalo.class, config.getInitialBuffalo());
        createAnimals(Duck.class, config.getInitialDucks());
        createAnimals(Caterpillar.class, config.getInitialCaterpillars());

        initializePlants(config.getInitialPlants());
        log.info("Animals initialization completed");
    }

    private <T extends Animal> void createAnimals(Class<T> animalClass, int count) {
        for (int i = 0; i < count; i++) {
            try {
                Animal animal = createAnimalInstance(animalClass);
                placeAnimalOnIsland(animal);
            } catch (Exception e) {
                log.error("Failed to create animal: {}", animalClass.getSimpleName(), e);
            }
        }
    }

    private Animal createAnimalInstance(Class<? extends Animal> animalClass)
            throws Exception {
        // Пробуем конструктор по умолчанию
        try {
            Animal animal = animalClass.getDeclaredConstructor().newInstance();

            // Устанавливаем параметры через рефлексию
            AnimalConfig config = AnimalConfig.valueOf(animalClass.getSimpleName().toUpperCase());

            var weightField = Animal.class.getDeclaredField("weight");
            weightField.setAccessible(true);
            weightField.set(animal, config.getWeight());

            var foodField = Animal.class.getDeclaredField("maxFoodRequired");
            foodField.setAccessible(true);
            foodField.set(animal, config.getMaxFoodRequired());

            var speedField = Animal.class.getDeclaredField("maxSpeed");
            speedField.setAccessible(true);
            speedField.set(animal, config.getMaxSpeed());

            return animal;
        } catch (NoSuchMethodException e) {
            // Если нет конструктора по умолчанию, используем параметризованный
            AnimalConfig config = AnimalConfig.valueOf(animalClass.getSimpleName().toUpperCase());
            return animalClass.getDeclaredConstructor(double.class, double.class, int.class)
                    .newInstance(config.getWeight(), config.getMaxFoodRequired(), config.getMaxSpeed());
        }
    }

    private void placeAnimalOnIsland(Animal animal) {
        int x = (int) (Math.random() * island.getWidth());
        int y = (int) (Math.random() * island.getHeight());

        Location location = island.getLocation(x, y);
        if (location != null && location.canAddAnimal(animal.getClass())) {
            location.addAnimal(animal);
        }
    }

    private void initializePlants(int plantCount) {
        for (int i = 0; i < plantCount; i++) {
            int x = (int) (Math.random() * island.getWidth());
            int y = (int) (Math.random() * island.getHeight());

            Location location = island.getLocation(x, y);
            if (location != null) {
                location.setPlantCount(location.getPlantCount() + 1);
            }
        }
    }

    public void runTurn() {
        if (!running || paused) {
            return;
        }

        try {
            long startTime = System.currentTimeMillis();

            // Обрабатываем все локации параллельно
            island.getLocations().parallelStream().forEach(location -> {
                locationExecutor.submit(() -> processLocation(location));
            });

            // Удаляем мертвых животных
            island.removeDeadAnimals();

            // Растения растут
            island.getLocations().forEach(Location::growPlants);

            // Сохраняем статистику
            if (island.getCurrentTurn() % properties.getStatisticsSaveInterval() == 0) {
                statisticsService.saveTurnStatistics(island);
            }

            island.incrementTurn();

            long endTime = System.currentTimeMillis();
            log.debug("Turn {} completed in {} ms", island.getCurrentTurn(), (endTime - startTime));

            checkStopConditions();

        } catch (Exception e) {
            log.error("Error during simulation turn: {}", e.getMessage(), e);
        }
    }

    private void processLocation(Location location) {
        if (!location.tryLock(100)) {
            return;
        }

        try {
            location.getAliveAnimals().forEach(animal -> {
                if (animal.isAlive()) {
                    try {
                        // ДВИЖЕНИЕ: обрабатываем здесь
                        processAnimalMovement(animal, location);

                        // Остальная логика
                        animal.eat();
                        animal.reproduce();
                        animal.decreaseSatiety();
                    } catch (Exception e) {
                        log.error("Error processing animal {}: {}", animal.getClass().getSimpleName(), e.getMessage());
                    }
                }
            });
        } finally {
            location.unlock();
        }
    }

    private void processAnimalMovement(Animal animal, Location currentLocation) {
        double moveProbability = getMoveProbability(animal);

        // Если вероятность 0 - животное не двигается
        if (moveProbability <= 0) {
            return;
        }

        if (Math.random() < moveProbability) {
            Location newLocation = getRandomAdjacentLocation(currentLocation);
            if (newLocation != null && newLocation.canAddAnimal(animal.getClass())) {
                if (newLocation.tryLock(50)) {
                    try {
                        currentLocation.removeAnimal(animal);
                        newLocation.addAnimal(animal);
                        animal.setCurrentLocation(newLocation);
                        log.trace("{} moved from [{},{}] to [{},{}]",
                                animal.getClass().getSimpleName(),
                                currentLocation.getX(), currentLocation.getY(),
                                newLocation.getX(), newLocation.getY());
                    } finally {
                        newLocation.unlock();
                    }
                }
            }
        }
    }

    private double getMoveProbability(Animal animal) {
        try {
            AnimalConfig config = AnimalConfig.valueOf(animal.getClass().getSimpleName().toUpperCase());
            return config.getMoveProbability();
        } catch (IllegalArgumentException e) {
            log.warn("No config found for animal: {}, using default move probability", animal.getClass().getSimpleName());
            return 0.7; // Значение по умолчанию
        }
    }

    private Location getRandomAdjacentLocation(Location currentLocation) {
        List<Location> adjacentLocations = new ArrayList<>();
        int x = currentLocation.getX();
        int y = currentLocation.getY();

        // Проверяем все 8 направлений
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int newX = x + dx;
                int newY = y + dy;

                if (island.isValidLocation(newX, newY)) {
                    Location adjacent = island.getLocation(newX, newY);
                    if (adjacent != null) {
                        adjacentLocations.add(adjacent);
                    }
                }
            }
        }

        if (adjacentLocations.isEmpty()) return null;
        return adjacentLocations.get((int) (Math.random() * adjacentLocations.size()));
    }

    private void checkStopConditions() {
        int totalAnimals = island.getTotalAnimals();

        if (totalAnimals == 0 && island.getCurrentTurn() >= properties.getMaxTurnsWithoutAnimals()) {
            log.info("Stopping simulation: no animals left");
            stopSimulation();
        }

        if (island.getCurrentTurn() >= properties.getMaxTotalTurns()) {
            log.info("Stopping simulation: reached maximum turns");
            stopSimulation();
        }
    }

    public void startSimulation() {
        if (!running) {
            running = true;
            paused = false;

            scheduledTask = scheduler.scheduleAtFixedRate(
                    this::runTurn,
                    0,
                    turnDurationMs,
                    TimeUnit.MILLISECONDS
            );
            log.info("Simulation started with speed: {} ms", turnDurationMs);
        }
    }

    public void pauseSimulation() {
        paused = true;
        log.info("Simulation paused");
    }

    public void resumeSimulation() {
        paused = false;
        log.info("Simulation resumed");
    }

    public void stopSimulation() {
        running = false;
        paused = false;

        if (scheduledTask != null) {
            scheduledTask.cancel(false);
            scheduledTask = null;
        }
        log.info("Simulation stopped");

        locationExecutor.shutdown();
    }

    public void setTurnDuration(int durationMs) {
        this.turnDurationMs = durationMs;
        log.info("Turn duration set to {} ms", durationMs);

        // Публикуем событие изменения скорости
        eventPublisher.publishEvent(new SpeedChangeEvent(durationMs));

        if (running && scheduledTask != null) {
            scheduledTask.cancel(false);
            scheduledTask = scheduler.scheduleAtFixedRate(
                    this::runTurn,
                    0,
                    turnDurationMs,
                    TimeUnit.MILLISECONDS
            );
            log.info("Scheduler restarted with new speed: {} ms", durationMs);
        }
    }

    public Map<String, Object> getStatistics() {
        return Map.of(
                "currentTurn", island.getCurrentTurn(),
                "totalAnimals", island.getTotalAnimals(),
                "totalPlants", island.getTotalPlants(),
                "islandWidth", island.getWidth(),
                "islandHeight", island.getHeight()
        );
    }

    public int getTurnDurationMs() {
        return this.turnDurationMs;
    }

    public boolean isPaused() {
        return paused;
    }

    @PreDestroy
    public void cleanup() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}