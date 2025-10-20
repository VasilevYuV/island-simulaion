package com.VasilevYuV.island.controller;

import com.VasilevYuV.island.event.SimulationPauseEvent;
import com.VasilevYuV.island.event.SpeedChangeEvent;
import com.VasilevYuV.island.island.Island;
import com.VasilevYuV.island.island.IslandState;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Controller
public class WebSocketController {
    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final Island island;

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    private int updateIntervalMs = 1000;
    private volatile boolean updatesPaused = false;

    public WebSocketController(SimpMessagingTemplate messagingTemplate, Island island) {
        this.messagingTemplate = messagingTemplate;
        this.island = island;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        startScheduledUpdates();
    }

    @EventListener
    public void handleSpeedChange(SpeedChangeEvent event) {
        setUpdateInterval(event.getSpeedMs());
    }

    @EventListener
    public void handleSimulationPause(SimulationPauseEvent event) {
        this.updatesPaused = event.isPaused();
    }

    private void startScheduledUpdates() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
        scheduledTask = scheduler.scheduleAtFixedRate(
                this::sendIslandUpdate,
                0,
                updateIntervalMs,
                TimeUnit.MILLISECONDS
        );
        log.info("WebSocket updates scheduled with interval: {} ms", updateIntervalMs);
    }

    public void sendIslandUpdate() {
        if (updatesPaused) {
            return;
        }
        IslandState state = island.getState();
        messagingTemplate.convertAndSend("/topic/island-update", state);
    }

    public void setUpdateInterval(int intervalMs) {
        this.updateIntervalMs = intervalMs;
        startScheduledUpdates();
    }

    @MessageMapping("/control")
    public void handleControlMessage(@Payload Map<String, Object> message) {
        messagingTemplate.convertAndSend("/topic/control-response", message);
    }

    @MessageMapping("/statistics")
    public void handleStatisticsRequest() {
        Map<String, Object> stats = Map.of(
                "currentTurn", island.getCurrentTurn(),
                "totalAnimals", island.getTotalAnimals(),
                "totalPlants", island.getTotalPlants()
        );
        messagingTemplate.convertAndSend("/topic/statistics", stats);
    }

    @PreDestroy
    public void cleanup() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}