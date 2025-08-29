#!/usr/bin/env bash
set -euo pipefail

PROJECT_ID="__PUT_YOURS__"
REGION="asia-northeast3"
SERVICE_NAME="__PUT_YOURS__"
SQL_INSTANCE="__PUT_YOURS__"        # ex) proj:asia-northeast3:sql-01
DB_NAME="__PUT_YOURS__"
DB_USER="__PUT_YOURS__"             # <- 필요하면 이것도 secret로
# DB_PASSWORD는 하드코딩 금지!

gcloud config set project "$PROJECT_ID"

# 배포 (소스에서 바로 빌드+배포, 프록시 불필요: Java Connector 사용)
gcloud run deploy "$SERVICE_NAME" \
  --source . \
  --region "$REGION" \
  --allow-unauthenticated \
  --service-account "$SA_EMAIL" \
  --set-env-vars "SPRING_PROFILES_ACTIVE=prod,DB_NAME=$DB_NAME,DB_USER=$DB_USER,SQL_INSTANCE=$SQL_INSTANCE" \
  --set-secrets "DB_PASSWORD=DB_PASSWORD:latest" \
  --set-env-vars "JAVA_TOOL_OPTIONS=-XX:MaxRAMPercentage=75" \
  --min-instances=1

echo "Deployed. Check logs next."
