# (A) Cloud SQL Proxy 바이너리 스테이지
FROM gcr.io/cloud-sql-connectors/cloud-sql-proxy:2.18.0 AS proxy

# (B) 빌드
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml ./
RUN mvn -B -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -q -DskipTests package

# (C) 런타임
FROM eclipse-temurin:21-jre
WORKDIR /app

# 앱 JAR
COPY --from=builder /app/target/*.jar /app/app.jar

# 프록시 바이너리 복사
COPY --from=proxy /cloud-sql-proxy /usr/local/bin/cloud-sql-proxy

# 시작 스크립트
COPY start.sh /app/start.sh
RUN chmod +x /usr/local/bin/cloud-sql-proxy /app/start.sh

ENV JAVA_OPTS=""
EXPOSE 8080
CMD ["/bin/sh", "/app/start.sh"]
