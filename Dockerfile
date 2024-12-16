FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /home/maven/src

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

EXPOSE 8085

COPY --from=build /home/maven/src/target/mrc-crm.jar /app/mrc-crm.jar

CMD ["java", "-jar", "/app/mrc-crm.jar"]
