# Build stage: compila el módulo common y luego complete-service (contexto de build = backend/)
FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /workspace
COPY pom.xml .
COPY common ./common
COPY complete-service ./complete-service
RUN mvn -pl common,complete-service -am package -DskipTests -q

FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
COPY --from=build /workspace/backend/complete-service/target/complete-service-*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
