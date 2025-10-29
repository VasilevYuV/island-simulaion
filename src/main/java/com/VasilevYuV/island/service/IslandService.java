package com.VasilevYuV.island.service;

import com.VasilevYuV.island.island.Island;
import com.VasilevYuV.island.location.Location;

public class IslandService {
    private static Island island;

    public static void setInstance(Island islandInstance) {
        island = islandInstance;
    }

    public static Location getLocation(int x, int y) {
        return island != null ? island.getLocation(x, y) : null;
    }

    public static boolean isValidLocation(int x, int y) {
        return island != null && island.isValidLocation(x, y);
    }

    // Методы для получения размеров острова
    public static int getWidth() {
        return island != null ? island.getWidth() : 0;
    }

    public static int getHeight() {
        return island != null ? island.getHeight() : 0;
    }
}