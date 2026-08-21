# Build stage: compila el módulo common y luego candystore-service (contexto de build = backend/)
FROM maven:3.9-eclipse-temurin-11 AS build
WORKDIR /workspace
COPY pom.xml .
COPY common ./common
COPY candystore-service ./candystore-service
COPY premieres-service/pom.xml ./premieres-service/pom.xml
COPY complete-service/pom.xml ./complete-service/pom.xml
RUN mvn -pl common,candystore-service -am package -DskipTests -q

FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
COPY --from=build /workspace/candystore-service/target/candystore-service-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
