#!/usr/bin/env bash
set -euo pipefail

PROJECT_ID="unisport-041219"
REGION="asia-northeast3"        # 서울
SERVICE_NAME="unisport-server"
SQL_INSTANCE="unisport-041219:asia-northeast3:unisport-instance"    # ex) ku-unisport-prod:asia-northeast3:unisql-01
DB_NAME="unisport-db"
DB_USER="unisport_user"
DB_PASSWORD="sebin"

gcloud config set project "$PROJECT_ID"

# 1) 배포 (Cloud Build가 자동으로 컨테이너 빌드)
gcloud run deploy "$SERVICE_NAME" \
  --source . \
  --region "$REGION" \
  --allow-unauthenticated \
  --set-env-vars "SPRING_PROFILES_ACTIVE=prod,DB_NAME=$DB_NAME,DB_USER=$DB_USER,DB_PASSWORD=$DB_PASSWORD,SQL_INSTANCE=$SQL_INSTANCE" \
  --set-env-vars "JAVA_TOOL_OPTIONS=-XX:MaxRAMPercentage=75" \
  --min-instances=1

# 2) (선택) Cloud SQL 연결 추가: 유닉스 소켓을 쓰려면 아래 옵션을 배포 시 추가
#  --add-cloudsql-instances="$SQL_INSTANCE"

echo "Deployed to Cloud Run."
