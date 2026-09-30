# Stage 1: Build application using Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /workspace

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build jar
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Lightweight runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /workspace/target/*.jar app.jar

ENV PORT=8082
EXPOSE 8082

# Start Spring Boot app, respecting Render's dynamic PORT variable
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8082}"]
