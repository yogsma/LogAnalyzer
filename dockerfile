FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./build/libs/loganalyzer-0.0.1-SNAPSHOT.jar app.jar
CMD exec java -jar app.jar