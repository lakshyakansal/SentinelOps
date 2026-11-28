CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE TABLE IF NOT EXISTS incidents (
 id BIGSERIAL PRIMARY KEY,
 service_name VARCHAR(160) NOT NULL,
 environment VARCHAR(80) NOT NULL,
 severity VARCHAR(20) NOT NULL,
 status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
 title VARCHAR(500) NOT NULL,
 raw_logs TEXT,
 root_cause TEXT,
 recommendations TEXT,
 ai_confidence VARCHAR(20),
 created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 resolved_at TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_incidents_created ON incidents(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_incidents_service ON incidents(service_name);
CREATE INDEX IF NOT EXISTS idx_incidents_status ON incidents(status);
CREATE TABLE IF NOT EXISTS historical_incidents (
 id BIGSERIAL PRIMARY KEY,
 service_name VARCHAR(160) NOT NULL,
 title TEXT NOT NULL,
 root_cause TEXT NOT NULL,
 resolution TEXT NOT NULL,
 log_text TEXT NOT NULL,
 embedding vector(384),
 created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
