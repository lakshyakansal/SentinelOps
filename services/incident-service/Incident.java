package com.sentinelops.incident;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="incidents")
public class Incident {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="service_name",nullable=false) private String serviceName;
 @Column(nullable=false) private String environment;
 @Column(nullable=false) private String severity;
 @Column(nullable=false) private String status="OPEN";
 @Column(nullable=false) private String title;
 @Column(name="raw_logs") private String rawLogs;
 @Column(name="root_cause") private String rootCause;
 @Column(name="recommendations") private String recommendations;
 @Column(name="ai_confidence") private String aiConfidence;
 @Column(name="created_at") private Instant createdAt=Instant.now();
 @Column(name="updated_at") private Instant updatedAt=Instant.now();
 @Column(name="resolved_at") private Instant resolvedAt;
 protected Incident(){}
 public Incident(String serviceName,String environment,String severity,String title,String rawLogs){
  this.serviceName=serviceName;this.environment=environment;this.severity=severity;this.title=title;this.rawLogs=rawLogs;
 }
 @PreUpdate void touch(){updatedAt=Instant.now();if("RESOLVED".equals(status))resolvedAt=Instant.now();}
 public Long getId(){return id;} public String getServiceName(){return serviceName;}
 public String getEnvironment(){return environment;} public String getSeverity(){return severity;}
 public String getStatus(){return status;} public String getTitle(){return title;}
 public String getRawLogs(){return rawLogs;} public String getRootCause(){return rootCause;}
 public String getRecommendations(){return recommendations;} public String getAiConfidence(){return aiConfidence;}
 public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
 public Instant getResolvedAt(){return resolvedAt;}
 public void updateStatus(String value){status=value;}
 public void applyAnalysis(AiResult r){rootCause=r.rootCause();recommendations=String.join("\n",r.recommendations());aiConfidence=r.confidence();}
}
