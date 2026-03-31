# ---- Build Stage ----
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Copy gradle wrapper and build files first for layer caching
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Make gradlew executable
RUN chmod +x gradlew

# Download dependencies (cached layer)
RUN ./gradlew dependencies --no-daemon || true

# Copy source code and build
COPY src src
RUN ./gradlew bootJar --no-daemon -x test

# ---- Run Stage ----
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Create non-root user for security
RUN addgroup --system spring && adduser --system --ingroup spring spring

COPY --from=builder /app/build/libs/*.jar app.jar

RUN chown spring:spring app.jar
USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
