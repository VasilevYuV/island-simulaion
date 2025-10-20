package com.VasilevYuV.island.repository;

import com.VasilevYuV.island.model.TurnStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnStatisticsRepository extends JpaRepository<TurnStatistics, Long> {

    List<TurnStatistics> findBySimulationRunIdAndSpeciesOrderByTurnNumber(Long simulationRunId, String species);

    @Query("SELECT ts FROM TurnStatistics ts WHERE ts.simulationRun.id = :runId AND ts.turnNumber = :turnNumber")
    List<TurnStatistics> findBySimulationRunAndTurnNumber(@Param("runId") Long runId, @Param("turnNumber") Integer turnNumber);

    @Query("SELECT MAX(ts.turnNumber) FROM TurnStatistics ts WHERE ts.simulationRun.id = :runId")
    Integer findMaxTurnNumberBySimulationRun(@Param("runId") Long runId);

    void deleteBySimulationRunId(Long simulationRunId);
}