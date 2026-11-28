package com.sentinelops.incident;
import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.Map;
@RestControllerAdvice public class ApiErrors {
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<?> missing(IllegalArgumentException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));}
}
