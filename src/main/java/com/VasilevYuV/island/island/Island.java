package com.VasilevYuV.island.island;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.location.Location;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Island {
    private int width;
    private int height;
    private List<Location> locations;
    private AtomicInteger currentTurn;
    private volatile boolean initialized;

    public Island() {
        this.width = 100;
        this.height = 20;
        this.locations = new CopyOnWriteArrayList<>();
        this.currentTurn = new AtomicInteger(0);
        this.initialized = false;
        initializeIsland();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public AtomicInteger getCurrentTurnAtomic() {
        return currentTurn;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void initialize(int width, int height) {
        this.width = width;
        this.height = height;
        this.locations.clear();
        this.currentTurn.set(0);
        initializeIsland();
    }

    private void initializeIsland() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations.add(new Location(x, y));
            }
        }
        initialized = true;
    }

    public Location getLocation(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        int index = x * height + y;
        return locations.get(index);
    }

    public void addAnimal(Animal animal, int x, int y) {
        Location location = getLocation(x, y);
        if (location != null) {
            location.addAnimal(animal);
        }
    }

    public void removeDeadAnimals() {
        locations.parallelStream().forEach(location -> {
            List<Animal> deadAnimals = location.getAnimals().stream()
                    .filter(animal -> !animal.isAlive())
                    .toList();

            deadAnimals.forEach(location::removeAnimal);
        });
    }

    public IslandState getState() {
        return new IslandState(this);
    }

    public void incrementTurn() {
        currentTurn.incrementAndGet();
    }

    public int getCurrentTurn() {
        return currentTurn.get();
    }

    public int getTotalAnimals() {
        return locations.stream()
                .mapToInt(Location::getAnimalCount)
                .sum();
    }

    public int getTotalPlants() {
        return locations.stream()
                .mapToInt(Location::getPlantCount)
                .sum();
    }
    public boolean isValidLocation(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}