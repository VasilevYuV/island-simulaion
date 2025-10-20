package com.VasilevYuV.island.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "simulation_runs")
public class SimulationRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(columnDefinition = "TEXT")
    private String configuration;

    @Column(name = "island_width")
    private Integer islandWidth;

    @Column(name = "island_height")
    private Integer islandHeight;

    @Column(name = "total_turns")
    private Integer totalTurns;

    @OneToMany(mappedBy = "simulationRun", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TurnStatistics> statistics = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        startedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public Integer getIslandWidth() {
        return islandWidth;
    }

    public void setIslandWidth(Integer islandWidth) {
        this.islandWidth = islandWidth;
    }

    public Integer getIslandHeight() {
        return islandHeight;
    }

    public void setIslandHeight(Integer islandHeight) {
        this.islandHeight = islandHeight;
    }

    public Integer getTotalTurns() {
        return totalTurns;
    }

    public void setTotalTurns(Integer totalTurns) {
        this.totalTurns = totalTurns;
    }

    public List<TurnStatistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<TurnStatistics> statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationRun that = (SimulationRun) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(startedAt, that.startedAt) &&
                Objects.equals(endedAt, that.endedAt) &&
                Objects.equals(configuration, that.configuration) &&
                Objects.equals(islandWidth, that.islandWidth) &&
                Objects.equals(islandHeight, that.islandHeight) &&
                Objects.equals(totalTurns, that.totalTurns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startedAt, endedAt, configuration, islandWidth, islandHeight, totalTurns);
    }

    @Override
    public String toString() {
        return "SimulationRun{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                ", configuration='" + configuration + '\'' +
                ", islandWidth=" + islandWidth +
                ", islandHeight=" + islandHeight +
                ", totalTurns=" + totalTurns +
                '}';
    }
}