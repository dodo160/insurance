FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY insurance-commons/pom.xml ./insurance-commons/
COPY insurance-app/pom.xml ./insurance-app/

COPY insurance-commons/src ./insurance-commons/src
COPY insurance-app/src ./insurance-app/src

RUN mvn -B -pl insurance-app -am clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/insurance-app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
