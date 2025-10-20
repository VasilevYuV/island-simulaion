package com.VasilevYuV.island.controller;

import com.VasilevYuV.island.controller.DTO.SimulationConfig;
import com.VasilevYuV.island.event.SimulationPauseEvent;
import com.VasilevYuV.island.event.SpeedChangeEvent;
import com.VasilevYuV.island.service.SimulationEngine;
import com.VasilevYuV.island.island.Island;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SimulationController {
    private final SimulationEngine simulationEngine;
    private final Island island;
    private final ApplicationEventPublisher eventPublisher;

    public SimulationController(SimulationEngine simulationEngine,
                                Island island,
                                ApplicationEventPublisher eventPublisher) {
        this.simulationEngine = simulationEngine;
        this.island = island;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/")
    public String configurator(Model model) {
        model.addAttribute("config", new SimulationConfig());
        return "configurator";
    }

    @PostMapping("/start")
    public String startSimulation(@ModelAttribute SimulationConfig config) {
        simulationEngine.initializeIsland(config.getWidth(), config.getHeight(), config);

        // Публикуем начальную скорость
        eventPublisher.publishEvent(new SpeedChangeEvent(config.getTurnDurationMs()));

        simulationEngine.startSimulation();
        return "redirect:/simulation";
    }

    @GetMapping("/simulation")
    public String simulationView(Model model) {
        model.addAttribute("island", island);
        return "simulation";
    }

    @PostMapping("/pause")
    @ResponseBody
    public String pauseSimulation() {
        simulationEngine.pauseSimulation();
        eventPublisher.publishEvent(new SimulationPauseEvent(true));
        return "Simulation paused";
    }

    @PostMapping("/resume")
    @ResponseBody
    public String resumeSimulation() {
        simulationEngine.resumeSimulation();
        eventPublisher.publishEvent(new SimulationPauseEvent(false));
        return "Simulation resumed";
    }

    @PostMapping("/stop")
    @ResponseBody
    public String stopSimulation() {
        simulationEngine.stopSimulation();
        eventPublisher.publishEvent(new SimulationPauseEvent(true));
        return "Simulation stopped";
    }

    @PostMapping("/api/control")
    @ResponseBody
    public String controlSimulation(@RequestParam String action,
                                    @RequestParam(required = false) Integer speed) {
        switch (action.toLowerCase()) {
            case "pause":
                simulationEngine.pauseSimulation();
                eventPublisher.publishEvent(new SimulationPauseEvent(true));
                return "paused";
            case "resume":
                simulationEngine.resumeSimulation();
                eventPublisher.publishEvent(new SimulationPauseEvent(false));
                return "resumed";
            case "stop":
                simulationEngine.stopSimulation();
                eventPublisher.publishEvent(new SimulationPauseEvent(true));
                return "stopped";
            case "speed":
                if (speed != null) {
                    simulationEngine.setTurnDuration(speed);
                    // Событие публикуется внутри setTurnDuration
                    return "speed changed to " + speed + "ms";
                }
            default:
                return "unknown action";
        }
    }
}