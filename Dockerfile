# ---- Stage 1: build jar ----
FROM maven:3.9-eclipse-temurin-8 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B clean package -DskipTests

# ---- Stage 2: runtime ----
FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /app/target/credit-simulator.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]