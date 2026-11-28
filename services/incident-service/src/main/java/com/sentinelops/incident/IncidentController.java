package com.sentinelops.incident;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import java.util.*;
@RestController @RequestMapping("/api/v1/incidents") @CrossOrigin(origins="*")
public class IncidentController {
 private final IncidentRepository repo; private final RestClient ai,notify;
 public IncidentController(IncidentRepository repo,@Value("${sentinelops.ai-url}") String aiUrl,@Value("${sentinelops.notification-url}") String notifyUrl){
  this.repo=repo;ai=RestClient.builder().baseUrl(aiUrl).build();notify=RestClient.builder().baseUrl(notifyUrl).build();
 }
 @GetMapping public List<Incident> all(){return repo.findAllByOrderByCreatedAtDesc();}
 @GetMapping("/{id}") public Incident get(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Incident not found: "+id));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Incident create(@Valid @RequestBody AlertIngestRequest req){
  Incident i=repo.save(new Incident(req.serviceName(),req.environment(),req.severity().toUpperCase(),req.message(),req.logs()));
  analyze(i); Incident saved=repo.save(i);
  try{notify.post().uri("/api/v1/notifications").body(Map.of("incidentId",saved.getId(),"title",saved.getTitle(),"severity",saved.getSeverity())).retrieve().toBodilessEntity();}catch(Exception ignored){}
  return saved;
 }
 @PatchMapping("/{id}/status") public Incident status(@PathVariable Long id,@Valid @RequestBody StatusUpdateRequest req){
  String s=req.status().toUpperCase();if(!List.of("OPEN","INVESTIGATING","MITIGATED","RESOLVED").contains(s))throw new IllegalArgumentException("Unsupported status");
  Incident i=get(id);i.updateStatus(s);return repo.save(i);
 }
 @PostMapping("/{id}/investigate") public Incident investigate(@PathVariable Long id){Incident i=get(id);analyze(i);return repo.save(i);}
 private void analyze(Incident i){
  try{AiResult r=ai.post().uri("/analyze").body(Map.of("service_name",i.getServiceName(),"logs",i.getRawLogs()==null?"":i.getRawLogs())).retrieve().body(AiResult.class);
   if(r!=null)i.applyAnalysis(r);
  }catch(Exception ex){i.applyAnalysis(new AiResult("AI analysis unavailable; manual investigation required.","UNKNOWN",
   List.of("Inspect stack traces","Check dependency health","Review recent deployments"),"LOW"));}
 }
}
