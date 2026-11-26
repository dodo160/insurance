FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY insurance-commons/pom.xml ./insurance-commons/
COPY insurance-app/pom.xml ./insurance-app/
COPY tariff-service/pom.xml ./tariff-service/

COPY insurance-commons/src ./insurance-commons/src
COPY insurance-app/src ./insurance-app/src
COPY tariff-service/src ./tariff-service/src

FROM build AS build-tariff
RUN mvn -B -pl tariff-service -am clean package -DskipTests

FROM build AS build-insurance
RUN mvn -B -pl insurance-app -am clean package -DskipTests

FROM eclipse-temurin:17-jre AS tariff
WORKDIR /app
COPY --from=build-tariff /app/tariff-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:17-jre AS insurance
WORKDIR /app
COPY --from=build-insurance /app/insurance-app/target/*.jar app.jar
COPY --from=build-insurance /app/insurance-app/src/main/resources/xsd ./src/main/resources/xsd
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
