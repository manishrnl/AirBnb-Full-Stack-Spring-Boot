# ==============================
# 1️⃣ Build Stage
# ==============================
FROM eclipse-temurin:21-jdk AS builder

# Install Maven (since we’re not using mvnw)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Set working directory inside the container
WORKDIR /app

# Copy only the Maven descriptor first (for better layer caching)
COPY pom.xml .

# Pre-download dependencies (cached between builds if pom.xml unchanged)
RUN mvn -B dependency:go-offline

# Copy the full source code
COPY src ./src

# Build the Spring Boot JAR (skip tests for faster build)
RUN mvn -B clean package -DskipTests


# ==============================
# 2️⃣ Runtime Stage
# ==============================
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Use a non-root user for security
RUN useradd -m spring
USER spring

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
