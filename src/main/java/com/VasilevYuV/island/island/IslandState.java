package com.VasilevYuV.island.island;

import com.VasilevYuV.island.location.Location;
import java.util.HashMap;
import java.util.Map;

public class IslandState {
    private final int width;
    private final int height;
    private final int currentTurn;
    private final int totalAnimals;
    private final int totalPlants;
    private final Map<String, Integer> speciesCount;
    private final LocationState[][] grid;

    public IslandState(Island island) {
        this.width = island.getWidth();
        this.height = island.getHeight();
        this.currentTurn = island.getCurrentTurn();
        this.totalAnimals = island.getTotalAnimals();
        this.totalPlants = island.getTotalPlants();
        this.speciesCount = new HashMap<>();
        this.grid = new LocationState[width][height];

        initializeGrid(island);
        countSpecies(island);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getTotalAnimals() {
        return totalAnimals;
    }

    public int getTotalPlants() {
        return totalPlants;
    }

    public Map<String, Integer> getSpeciesCount() {
        return speciesCount;
    }

    public LocationState[][] getGrid() {
        return grid;
    }

    private void initializeGrid(Island island) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = island.getLocation(x, y);
                grid[x][y] = new LocationState(location);
            }
        }
    }

    private void countSpecies(Island island) {
        island.getLocations().forEach(location -> {
            location.getAnimals().forEach(animal -> {
                if (animal.isAlive()) {
                    String species = animal.getClass().getSimpleName().toLowerCase();
                    speciesCount.merge(species, 1, Integer::sum);
                }
            });
        });
    }

    public static class LocationState {
        private final int x;
        private final int y;
        private final int plantCount;
        private final int animalCount;
        private final String dominantSpecies;

        public LocationState(Location location) {
            this.x = location.getX();
            this.y = location.getY();
            this.plantCount = location.getPlantCount();
            this.animalCount = location.getAnimalCount();
            this.dominantSpecies = findDominantSpecies(location);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getPlantCount() {
            return plantCount;
        }

        public int getAnimalCount() {
            return animalCount;
        }

        public String getDominantSpecies() {
            return dominantSpecies;
        }

        private String findDominantSpecies(Location location) {
            if (location.getAnimalCount() == 0) return "";

            Map<String, Integer> speciesCount = new HashMap<>();
            location.getAnimals().forEach(animal -> {
                if (animal.isAlive()) {
                    String species = animal.getClass().getSimpleName().toLowerCase();
                    speciesCount.merge(species, 1, Integer::sum);
                }
            });

            return speciesCount.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("");
        }
    }
}