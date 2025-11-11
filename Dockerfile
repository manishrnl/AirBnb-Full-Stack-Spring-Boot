# Use Eclipse Temurin JDK 21 (official image)
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy Maven wrapper and pom.xml files first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (caches dependencies to speed up rebuilds)
RUN ./mvnw dependency:go-offline

# Copy the rest of the project
COPY . .

# Build the Spring Boot app (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot’s default port
EXPOSE 8080

# Run the built JAR
CMD ["java", "-jar", "target/*.jar"]
