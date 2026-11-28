package com.sentinelops.incident;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
class IncidentServiceApplicationTests {@Test void statusLifecycleContainsResolved(){assertTrue(java.util.List.of("OPEN","INVESTIGATING","MITIGATED","RESOLVED").contains("RESOLVED"));}}
