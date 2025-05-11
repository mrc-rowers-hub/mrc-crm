# -------- Build stage --------
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /home/maven/src

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# -------- Runtime stage --------
FROM eclipse-temurin:21-jre

WORKDIR /app

EXPOSE 8085

# Copy jar from build stage
COPY --from=build /home/maven/src/target/mrc-crm.jar ./mrc-crm.jar

# Copy wait-for-it script
COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh

# Use wait-for-it to ensure MySQL is ready
CMD ["./wait-for-it.sh", "mrc-mysql:3306", "--", "java", "-jar", "mrc-crm.jar"]
