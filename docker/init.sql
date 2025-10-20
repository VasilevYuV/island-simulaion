-- Создание расширения для JSONB если не существует
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Создание индексов для оптимизации запросов
CREATE INDEX IF NOT EXISTS idx_turn_statistics_species
    ON turn_statistics(species, turn_number);

CREATE INDEX IF NOT EXISTS idx_turn_statistics_turn
    ON turn_statistics(turn_number DESC);

CREATE INDEX IF NOT EXISTS idx_turn_statistics_run
    ON turn_statistics(simulation_run_id);

CREATE INDEX IF NOT EXISTS idx_simulation_runs_date
    ON simulation_runs(started_at DESC);

-- Комментарии к таблицам
COMMENT ON TABLE simulation_runs IS 'Storage for simulation runs metadata';
COMMENT ON TABLE turn_statistics IS 'Population statistics for each simulation turn';