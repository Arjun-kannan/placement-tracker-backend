# syntax=docker/dockerfile:1

# STEP 1: Use JDK base image to build the app
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

# Copy Maven files first for better caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (skip tests to speed up)
RUN ./mvnw dependency:go-offline -DskipTests

# Now copy the source and build the jar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# STEP 2: Use JRE base image to run the app
FROM eclipse-temurin:17-jre-jammy

# Create a user to run the app securely
RUN adduser --disabled-password --gecos "" appuser
USER appuser

WORKDIR /app

# Copy the fat jar built in the previous step
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]