# Giai đoạn build
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Giai đoạn chạy ứng dụng
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY --from=build /app/target/javaspringboot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]