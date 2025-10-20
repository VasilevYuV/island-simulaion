package com.VasilevYuV.island.service;

import com.VasilevYuV.island.island.Island;
import com.VasilevYuV.island.model.SimulationRun;
import com.VasilevYuV.island.model.TurnStatistics;
import com.VasilevYuV.island.repository.SimulationRunRepository;
import com.VasilevYuV.island.repository.TurnStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {
    private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    private final SimulationRunRepository simulationRunRepository;
    private final TurnStatisticsRepository turnStatisticsRepository;

    private SimulationRun currentRun;

    public StatisticsService(SimulationRunRepository simulationRunRepository,
                             TurnStatisticsRepository turnStatisticsRepository) {
        this.simulationRunRepository = simulationRunRepository;
        this.turnStatisticsRepository = turnStatisticsRepository;
    }

    public SimulationRun getCurrentRun() {
        return currentRun;
    }

    @Transactional
    public void startNewSimulation(String name, String configuration, int width, int height) {
        currentRun = new SimulationRun();
        currentRun.setName(name);
        currentRun.setConfiguration(configuration);
        currentRun.setIslandWidth(width);
        currentRun.setIslandHeight(height);

        simulationRunRepository.save(currentRun);
        log.info("Started new simulation: {}", name);
    }

    @Transactional
    public void saveTurnStatistics(Island island) {
        if (currentRun == null) return;

        try {
            Map<String, Integer> speciesCount = countSpecies(island);

            speciesCount.forEach((species, count) -> {
                TurnStatistics stats = new TurnStatistics();
                stats.setSimulationRun(currentRun);
                stats.setTurnNumber(island.getCurrentTurn());
                stats.setSpecies(species);
                stats.setPopulationCount(count);

                turnStatisticsRepository.save(stats);
            });

            // Обновляем общее количество тактов
            currentRun.setTotalTurns(island.getCurrentTurn());
            simulationRunRepository.save(currentRun);

            log.debug("Saved statistics for turn {}", island.getCurrentTurnAtomic());

        } catch (Exception e) {
            log.error("Error saving statistics: {}", e.getMessage(), e);
        }
    }

    private Map<String, Integer> countSpecies(Island island) {
        Map<String, Integer> counts = new HashMap<>();

        island.getLocations().forEach(location -> {
            location.getAliveAnimals().forEach(animal -> {
                String species = animal.getClass().getSimpleName().toLowerCase();
                counts.merge(species, 1, Integer::sum);
            });
        });

        return counts;
    }

    @Transactional
    public void endCurrentSimulation() {
        if (currentRun != null) {
            currentRun.setEndedAt(LocalDateTime.now());
            simulationRunRepository.save(currentRun);
            log.info("Ended simulation: {}", currentRun.getName());
            currentRun = null;
        }
    }
}