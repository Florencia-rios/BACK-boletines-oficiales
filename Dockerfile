FROM maven:3.8-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/BACK-boletines-oficiales-0.0.1-SNAPSHOT.jar .
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "BACK-boletines-oficiales-0.0.1-SNAPSHOT.jar"]