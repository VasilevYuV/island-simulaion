package com.VasilevYuV.island.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "turn_statistics")
public class TurnStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_run_id", nullable = false)
    private SimulationRun simulationRun;

    @Column(name = "turn_number", nullable = false)
    private Integer turnNumber;

    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "population_count", nullable = false)
    private Integer populationCount;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() {
        recordedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimulationRun getSimulationRun() {
        return simulationRun;
    }

    public void setSimulationRun(SimulationRun simulationRun) {
        this.simulationRun = simulationRun;
    }

    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Integer turnNumber) {
        this.turnNumber = turnNumber;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Integer getPopulationCount() {
        return populationCount;
    }

    public void setPopulationCount(Integer populationCount) {
        this.populationCount = populationCount;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnStatistics that = (TurnStatistics) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(turnNumber, that.turnNumber) &&
                Objects.equals(species, that.species) &&
                Objects.equals(populationCount, that.populationCount) &&
                Objects.equals(recordedAt, that.recordedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, turnNumber, species, populationCount, recordedAt);
    }

    @Override
    public String toString() {
        return "TurnStatistics{" +
                "id=" + id +
                ", turnNumber=" + turnNumber +
                ", species='" + species + '\'' +
                ", populationCount=" + populationCount +
                ", recordedAt=" + recordedAt +
                '}';
    }
}