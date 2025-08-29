#!/bin/sh
set -e

# Cloud SQL Proxy 시작 (컨테이너 내부에서만 열어두면 되니 127.0.0.1 권장)
# 서비스 계정 JSON은 Render Secret File로 /etc/secrets/gcp-sql-key.json에 마운트한다고 가정
/usr/local/bin/cloud-sql-proxy \
  --port 3307 \
  --address 127.0.0.1 \
  --credentials-file /etc/secrets/gcp-sql-key.json \
  "$INSTANCE_CONNECTION_NAME" &

# 프록시가 뜰 시간 1~2초만 대기(옵션)
sleep 2

# Spring Boot 실행 (Render가 $PORT 주입)
exec java $JAVA_OPTS -Dserver.port="${PORT:-8080}" -jar /app/app.jar
