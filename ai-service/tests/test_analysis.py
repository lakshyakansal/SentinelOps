from fastapi.testclient import TestClient
from app.main import app
client=TestClient(app)
def test_health():
    assert client.get("/health").json()["status"]=="UP"
def test_pool_exhaustion():
    body=client.post("/analyze",json={"service_name":"payments","logs":"HikariPool connection is not available timeout"}).json()
    assert "connection pool" in body["rootCause"].lower()
    assert body["recommendations"]
def test_unknown_logs_low_confidence():
    body=client.post("/analyze",json={"service_name":"x","logs":"ordinary message"}).json()
    assert body["confidence"]=="LOW"
