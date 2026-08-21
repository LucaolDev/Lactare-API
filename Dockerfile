FROM maven:3.9.16-eclipse-temurin-21 AS build
WORKDIR /opt/app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /opt/app
COPY --from=build /opt/app/target/*.jar app.jar
RUN mkdir -p /opt/app/data
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
