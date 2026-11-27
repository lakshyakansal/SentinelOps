# 45-Day Engineering Roadmap

This roadmap describes a suggested implementation sequence for evolving the current runnable MVP. Milestones below are planned work unless the corresponding capability already exists in the codebase.

| Phase | Days | Milestone | Expected outcome |
|---|---:|---|---|
| 1 | 1–5 | Foundation | Domain model, service contracts, Compose environment, database schema |
| 2 | 6–10 | Incident workflow | Alert ingestion, incident lifecycle, validation, API tests |
| 3 | 11–15 | Reliable messaging | Kafka, transactional outbox, idempotent consumers, retry and DLQ |
| 4 | 16–20 | Observability ingestion | OpenTelemetry logs, metrics, traces, OpenSearch indexing |
| 5 | 21–25 | Persistent RAG | Embedding ingestion, pgvector similarity queries, retrieval evaluation |
| 6 | 26–30 | LLM quality and safety | Structured output, schema validation, guardrails, evaluation dataset |
| 7 | 31–35 | Incident intelligence | Alert correlation, anomaly detection, deployment context |
| 8 | 36–40 | Operator experience | Service map, SLO analytics, notifications, postmortem workflow |
| 9 | 41–45 | Hardening and release | Security review, load tests, AWS deployment, CI/CD, demo documentation |

## Delivery principles

- Keep each service contract versioned and documented.
- Add automated tests before introducing distributed retries.
- Make event consumers idempotent.
- Measure retrieval and LLM quality using labelled examples.
- Keep AI recommendations advisory.
- Require human approval for any future operational action.
- Do not mark a roadmap item complete until its implementation and tests are present.
