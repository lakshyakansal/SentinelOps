# Contributing

Thank you for considering a contribution to SentinelOps 2.0.

## Development workflow

1. Fork the repository and create a focused feature branch.
2. Keep changes scoped to one service or documented capability.
3. Update the relevant API or architecture documentation when contracts change.
4. Add or update tests for behavior changes.
5. Run the checks relevant to the files you changed.
6. Open a pull request describing the motivation, implementation, validation, and any limitations.

## Engineering guidelines

- Keep service boundaries and API contracts explicit.
- Validate external input at service boundaries.
- Do not commit secrets, local environment files, generated build output, or production data.
- Treat logs and retrieved incident content as untrusted.
- Keep AI-generated recommendations advisory.
- Mark roadmap work as planned until the code and tests exist.

## Local checks

```bash
# AI service
cd ai-service
pip install -r requirements.txt
pytest

# Java service (run from each service directory)
mvn test

# Frontend
cd frontend
npm install
npm run build
```

For the full local stack, see the root README.
