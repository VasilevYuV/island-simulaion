package com.VasilevYuV.island.event;

public class SimulationPauseEvent {
    private final boolean paused;

    public SimulationPauseEvent(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}