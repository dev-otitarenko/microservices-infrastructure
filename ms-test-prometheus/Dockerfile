FROM openjdk:11

VOLUME ["/tmp", "/tmp/logs"]

ADD /target/*.jar app.jar

EXPOSE 8801

CMD java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker /app.jar