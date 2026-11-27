# AWS Deployment Guide

This document describes a possible deployment architecture. It is not an included Terraform, CloudFormation, or CDK implementation, and the repository is not automatically deployed to AWS.

## Suggested portfolio architecture

| Concern | Suggested AWS service |
|---|---|
| Container registry | Amazon ECR |
| Container runtime | Amazon ECS on Fargate |
| Public entry point | Application Load Balancer |
| TLS certificates | AWS Certificate Manager |
| Relational database | Amazon RDS for PostgreSQL |
| Secrets | AWS Secrets Manager |
| Logs and metrics | Amazon CloudWatch |
| Object storage (future) | Amazon S3 |
| Cache (if introduced) | Amazon ElastiCache |

Verify pgvector extension availability and version compatibility for the selected RDS PostgreSQL engine, region, and parameter configuration before depending on vector search.

## Deployment sequence

1. Create a VPC with public load-balancer subnets and private application/data subnets.
2. Create ECR repositories for the services that will be deployed.
3. Build and push versioned container images.
4. Provision RDS PostgreSQL in private subnets and configure restricted security groups.
5. Apply the database schema using a controlled migration task.
6. Store database credentials, webhook secrets, and other sensitive configuration in Secrets Manager.
7. Create ECS task definitions and services with health checks and private service discovery.
8. Route public traffic through an ALB with ACM-managed TLS.
9. Keep PostgreSQL and internal microservices inaccessible from the public internet.
10. Configure CloudWatch log groups, alarms, dashboards, autoscaling, and deployment rollback.
11. Configure AWS Budgets and cost alerts.

## CI/CD identity

Prefer GitHub Actions OpenID Connect (OIDC) with a narrowly scoped IAM role instead of long-lived AWS access keys. Restrict the role trust policy to the intended repository and branch/environment.

## Production checks

- Confirm container health checks and graceful shutdown behavior.
- Set CPU, memory, and autoscaling policies based on load tests.
- Configure database backups, retention, and restore drills.
- Review NAT gateway, load balancer, database, logging, and always-on compute costs.
- Run security scanning and dependency review.
- Validate TLS, CORS, secrets, and network access rules.

For an EKS deployment, additional work is required for Helm, IRSA, ingress/load-balancer integration, autoscaling, network policies, and managed data services.
