# Build stage: compila el módulo common y luego premieres-service (contexto de build = backend/)
FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /workspace
COPY pom.xml .
COPY common ./common
COPY premieres-service ./premieres-service
RUN mvn -pl common,premieres-service -am package -DskipTests -q

# Runtime stage
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
COPY --from=build /workspace/premieres-service/target/premieres-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
