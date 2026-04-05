FROM eclipse-temurin:21-jdk-alpine
COPY target/*.jar app.jar
LABEL authors="hristo"
ENTRYPOINT ["java", "-jar", "/app.jar"]
