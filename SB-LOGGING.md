# Configuring Logging to ELK in Spring Boot Application

We need first to add these maven dependencies to each of our microservices:

```
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>5.1</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-core</artifactId>
    </dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
</dependency>
```

[Then add the config class](./ms-test-prometheus/src/main/java/com/maestro/app/ms/practice/prometheus/configuration/LoggingConfiguration.java)

And finally add these properties in your application.yml/application.properties:
```
logstash.host = localhost
logstash.port = 5000
logstash.queue-size = 512
```