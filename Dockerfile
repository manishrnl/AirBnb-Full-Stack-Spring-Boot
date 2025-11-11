# Use Eclipse Temurin JDK 21 (official image)
FROM eclipse-temurin:21-jdk

# Install Maven (since we’re not using mvnw)
RUN apt-get update && apt-get install -y maven

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml first (to leverage Docker layer caching)
COPY pom.xml .

# Download dependencies in advance (this speeds up subsequent builds)
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY . .

# Build the Spring Boot application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Expose Spring Boot's default port
EXPOSE 8080

# Run the built JAR
CMD ["java", "-jar", "target/*.jar"]
