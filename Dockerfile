FROM eclipse-temurin:8-jre

WORKDIR /app

COPY target/credit-simulator.jar app.jar

ENTRYPOINT  ["java","-jar","app.jar"]