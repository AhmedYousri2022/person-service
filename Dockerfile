FROM eclipse-temurin:17-jre AS builder
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} person.jar

ENTRYPOINT ["java","-jar","/person.jar"]