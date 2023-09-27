FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/export_report-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app.jar"]