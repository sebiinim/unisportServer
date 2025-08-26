# build stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# 1. 의존성 먼저 다운받도록 pom.xml만 복사
COPY pom.xml ./
RUN mvn -B -q -DskipTests dependency:go-offline

# 2. 소스 복사
COPY src ./src

# 3. 패키징
RUN mvn -B -q -DskipTests package

# run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
CMD ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
