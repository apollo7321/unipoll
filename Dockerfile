FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=build/libs/unipoll-1.0.5.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
