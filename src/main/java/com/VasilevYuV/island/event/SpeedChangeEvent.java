package com.VasilevYuV.island.event;

public class SpeedChangeEvent {
    private final int speedMs;

    public SpeedChangeEvent(int speedMs) {
        this.speedMs = speedMs;
    }

    public int getSpeedMs() {
        return speedMs;
    }
}