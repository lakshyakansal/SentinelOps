# SentinelOps 2.0
AI-assisted Production Incident Intelligence Platform

A runnable portfolio foundation using Java 21 / Spring Boot microservices, Python FastAPI, PostgreSQL + pgvector, React + TypeScript, Docker Compose and an optional Ollama LLM.

## Start
1. `cp .env.example .env`
2. `docker compose up --build`
3. Dashboard: http://localhost:5173
4. API gateway: http://localhost:8080
5. AI docs: http://localhost:8000/docs

Submit a demo alert:
```bash
curl -X POST http://localhost:8080/api/v1/alerts -H 'Content-Type: application/json' -d '{"serviceName":"payment-service","environment":"production","severity":"HIGH","message":"Payment requests failing","logs":"HikariPool-1 - Connection is not available, request timed out after 30000ms"}'
```

## Services
- alert-service :8081 — validates incoming alerts and forwards them to incident service
- incident-service :8082 — persists incidents, calls AI analysis, manages status
- notification-service :8083 — optional webhook dispatcher
- ai-service :8000 — evidence-based log analysis, RAG retrieval and optional Ollama
- frontend :5173 — React incident dashboard
- gateway :8080 — local Nginx API edge
- postgres :5432 — PostgreSQL + pgvector

## Local development
Start database: `docker compose up postgres -d`
AI: `cd ai-service && python -m venv .venv && source .venv/bin/activate && pip install -r requirements.txt && uvicorn app.main:app --reload`
Java services: `cd services/<service-name> && mvn spring-boot:run`
Frontend: `cd frontend && npm install && npm run dev`

## Important scope
This is a runnable MVP foundation, not a production-certified autonomous SRE system. Kafka, OpenSearch, full OTel ingestion, OIDC/RBAC, tenant isolation, Kubernetes remediation, and production AWS infrastructure are documented extension milestones. AI output is advisory; no automated infrastructure action is executed.

See `docs/` for architecture, API, RAG, security, AWS and 45-day roadmap.
