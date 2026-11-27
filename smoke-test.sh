#!/usr/bin/env bash
set -euo pipefail
curl -fsS http://localhost:8000/health
echo
curl -fsS -X POST http://localhost:8080/api/v1/alerts -H 'Content-Type: application/json' -d '{"serviceName":"payment-service","environment":"production","severity":"HIGH","message":"Payment API degraded","logs":"HikariPool connection is not available after timeout"}'
echo
curl -fsS http://localhost:8080/api/v1/incidents
echo
