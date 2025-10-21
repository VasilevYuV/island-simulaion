package com.VasilevYuV.island.location;

import com.VasilevYuV.island.animals.Animal;
import com.VasilevYuV.island.config.AnimalConfig;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Location {
    private final int x;
    private final int y;
    private final List<Animal> animals;
    private final Lock lock;
    private int plantCount;

    public Location(int x, int y, int plantCount) {
        this.x = x;
        this.y = y;
        this.animals = new CopyOnWriteArrayList<>();
        this.lock = new ReentrantLock();
        this.plantCount = plantCount;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public Lock getLock() {
        return lock;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public void setPlantCount(int plantCount) {
        this.plantCount = plantCount;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public boolean tryLock() {
        return lock.tryLock();
    }

    public boolean tryLock(long timeout) {
        try {
            return lock.tryLock(timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setCurrentLocation(this);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void growPlants() {
        plantCount = Math.min(plantCount + 2, 20);
    }

    public void consumePlants(int amount) {
        plantCount = Math.max(0, plantCount - amount);
    }

    public int getAnimalCount() {
        return (int) animals.stream().filter(Animal::isAlive).count();
    }

    public List<Animal> getAliveAnimals() {
        return animals.stream()
                .filter(Animal::isAlive)
                .toList();
    }

    public boolean canAddAnimal(Class<? extends Animal> animalType) {
        int currentCount = getAnimalsCount(animalType);
        int maxCount = getMaxAnimalsPerType(animalType);
        return currentCount < maxCount;
    }

    public int getAnimalsCount(Class<? extends Animal> animalType) {
        return (int) animals.stream()
                .filter(animal -> animalType.isInstance(animal) && animal.isAlive())
                .count();
    }

    public int getMaxAnimalsPerType(Class<? extends Animal> animalType) {
        try {
            // Получаем конфиг для этого типа животного
            AnimalConfig config = AnimalConfig.valueOf(animalType.getSimpleName().toUpperCase());
            return config.getMaxPerLocation();
        } catch (IllegalArgumentException e) {
            return 10; // значение по умолчанию
        }
    }
}