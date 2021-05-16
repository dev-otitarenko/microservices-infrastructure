FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim

VOLUME ["/tmp", "/tmp/logs"]

ADD /target/*.jar app.jar

EXPOSE 8803

CMD java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /app.jar