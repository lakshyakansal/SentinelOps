# API Reference

## Base URLs

| Component | Local URL |
|---|---|
| Nginx gateway | `http://localhost:8080` |
| AI service | `http://localhost:8000` |

The gateway forwards the application API routes to the corresponding internal services.

## Alert ingestion

### `POST /api/v1/alerts`

Creates an incident from an incoming alert.

Example request:

```json
{
  "serviceName": "payment-service",
  "environment": "production",
  "severity": "HIGH",
  "message": "Payment requests failing",
  "logs": "HikariPool-1 - Connection is not available"
}
```

The alert service validates the required fields and forwards the request to Incident Service. The response contains an `accepted` flag and the created incident payload.

## Incidents

| Method | Path | Purpose |
|---|---|---|
| `GET` | `/api/v1/incidents` | List incidents, newest first |
| `GET` | `/api/v1/incidents/{id}` | Retrieve one incident |
| `PATCH` | `/api/v1/incidents/{id}/status` | Update incident status |
| `POST` | `/api/v1/incidents/{id}/investigate` | Run AI analysis again |

### Update status

`PATCH /api/v1/incidents/{id}/status`

```json
{
  "status": "INVESTIGATING"
}
```

Supported values:

- `OPEN`
- `INVESTIGATING`
- `MITIGATED`
- `RESOLVED`

### Re-run investigation

`POST /api/v1/incidents/{id}/investigate`

Re-submits the incident service name and stored raw logs to the AI service, then saves the returned analysis.

## AI service

### `GET /health`

Returns a simple service health response and configured LLM provider.

### `POST /analyze`

Example request:

```json
{
  "service_name": "payment-service",
  "logs": "Connection is not available; request timed out"
}
```

The response includes:

- `rootCause`: diagnostic hypothesis.
- `severity`: severity inferred from recognized log signatures, or `UNKNOWN`.
- `recommendations`: suggested investigation steps.
- `confidence`: qualitative rule confidence label; not a calibrated probability.
- `evidence`: matched signature and retrieval notes.
- `similarIncidents`: demonstration retrieval matches.
- `generatedBy`: analysis path used.

## Operational notes

- The current API does not implement user authentication or role-based authorization.
- Internal service ports are published by the development Compose file for local testing.
- Request and response details should be verified against the running version before integrating external clients.
- Error response formats may differ between services; a unified API error contract is a production hardening item.
