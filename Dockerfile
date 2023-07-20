FROM eclipse-temurin:17-jre AS builder
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} person-1.0.0.jar

ENTRYPOINT ["java","-jar","/person-1.0.0.jar"]
