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

Now let’s run our microservice and go to <b>Kibana</b> and start playing with 
our index.

In <b>Kibana</b>, go to "Management" and click on create index pattern:
![Alt text](./docs/kibana-1.png?raw=true "Kibana-1")

We will find the name of the index mentioned in the logstash config file.
Now go to the section named "Discover" and you will find the log flow generated 
by our microservices:

![Alt text](./docs/kibana-2.png?raw=true "kibana-2")

Now you can play with kibana and generate the cool dashboards depending on what you need.