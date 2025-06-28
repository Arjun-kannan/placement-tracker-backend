# syntax=docker/dockerfile:1

################################################################################
# Step 1: Resolve and download dependencies
FROM eclipse-temurin:17-jdk-jammy as deps

WORKDIR /build

# Copy Maven wrapper with executable permissions
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

# Ensure wrapper is executable
RUN chmod +x mvnw

# Download dependencies (no BuildKit mount)
RUN ./mvnw dependency:go-offline -DskipTests

################################################################################
# Step 2: Build the application
FROM deps as package

WORKDIR /build

COPY src ./src

RUN ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################################################################################
# Step 3: Extract Spring Boot layers
FROM package as extract

WORKDIR /build
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

################################################################################
# Step 4: Final image for runtime
FROM eclipse-temurin:17-jre-jammy AS final

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

WORKDIR /app

# Copy Spring Boot layer folders from extract stage
COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
