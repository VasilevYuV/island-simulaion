package com.VasilevYuV.island.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "island.simulation")
public class SimulationProperties {
    private int minWidth = 50;
    private int maxWidth = 200;
    private int minHeight = 10;
    private int maxHeight = 50;
    private int defaultWidth = 100;
    private int defaultHeight = 20;
    private int turnDurationMs = 1000;
    private int statisticsSaveInterval = 10;
    private int maxTurnsWithoutAnimals = 100;
    private int maxTotalTurns = 10000;

    // Getters and Setters
    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public int getTurnDurationMs() {
        return turnDurationMs;
    }

    public void setTurnDurationMs(int turnDurationMs) {
        this.turnDurationMs = turnDurationMs;
    }

    public int getStatisticsSaveInterval() {
        return statisticsSaveInterval;
    }

    public void setStatisticsSaveInterval(int statisticsSaveInterval) {
        this.statisticsSaveInterval = statisticsSaveInterval;
    }

    public int getMaxTurnsWithoutAnimals() {
        return maxTurnsWithoutAnimals;
    }

    public void setMaxTurnsWithoutAnimals(int maxTurnsWithoutAnimals) {
        this.maxTurnsWithoutAnimals = maxTurnsWithoutAnimals;
    }

    public int getMaxTotalTurns() {
        return maxTotalTurns;
    }

    public void setMaxTotalTurns(int maxTotalTurns) {
        this.maxTotalTurns = maxTotalTurns;
    }

    public void validate() {
        if (defaultWidth < minWidth || defaultWidth > maxWidth) {
            throw new IllegalArgumentException("Default width must be between " + minWidth + " and " + maxWidth);
        }
        if (defaultHeight < minHeight || defaultHeight > maxHeight) {
            throw new IllegalArgumentException("Default height must be between " + minHeight + " and " + maxHeight);
        }
    }

    @Override
    public String toString() {
        return "SimulationProperties{" +
                "minWidth=" + minWidth +
                ", maxWidth=" + maxWidth +
                ", minHeight=" + minHeight +
                ", maxHeight=" + maxHeight +
                ", defaultWidth=" + defaultWidth +
                ", defaultHeight=" + defaultHeight +
                ", turnDurationMs=" + turnDurationMs +
                ", statisticsSaveInterval=" + statisticsSaveInterval +
                ", maxTurnsWithoutAnimals=" + maxTurnsWithoutAnimals +
                ", maxTotalTurns=" + maxTotalTurns +
                '}';
    }
}