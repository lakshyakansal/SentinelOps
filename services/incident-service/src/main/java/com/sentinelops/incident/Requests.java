package com.sentinelops.incident;
import jakarta.validation.constraints.*;
record AlertIngestRequest(@NotBlank String serviceName,@NotBlank String environment,@NotBlank String severity,@NotBlank String message,String logs){}
record StatusUpdateRequest(@NotBlank String status){}
record AiResult(String rootCause,String severity,java.util.List<String> recommendations,String confidence){}
