package com.sentinelops.alert;
import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.beans.factory.annotation.Value;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import org.springframework.web.client.RestClient;import java.util.Map;
@RestController @RequestMapping("/api/v1/alerts") @CrossOrigin(origins="*")
public class AlertController {
 record AlertRequest(@NotBlank String serviceName,@NotBlank String environment,@NotBlank String severity,@NotBlank String message,String logs){}
 private final RestClient client;
 public AlertController(@Value("${sentinelops.incident-url}") String url){client=RestClient.builder().baseUrl(url).build();}
 @PostMapping public ResponseEntity<?> ingest(@Valid @RequestBody AlertRequest req){
  Object incident=client.post().uri("/api/v1/incidents").body(req).retrieve().body(Object.class);
  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("accepted",true,"incident",incident));
 }
}
