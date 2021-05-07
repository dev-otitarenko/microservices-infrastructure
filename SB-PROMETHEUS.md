# Support PROMETHEUS in Spring Boot Application

Spring boot uses Micrometer an application metrics facade to integrate actuator metrics 
with external monitoring systems. It supports several monitoring systems like SignalFx, 
Graphite, Wavefront, Prometheus.

To integrate actuator with Prometheus, you need to add the following dependencies to each 
of your microservices:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```
and the following properties in application.properties file:
```
management.endpoint.metrics.enabled=true
management.endpoints.web.exposure.include=*
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
```
Once you add the above dependency, Spring Boot will automatically configure a PrometheusMeterRegistry 
to collect and export metrics data in a format that can be scraped by a Prometheus server.
To explore prometheus endpoint go to http://localhost:<app port>/actuator
we’re going to work on the product microservice for example.

![Alt text](./docs/service-actuator.png?raw=true "Calling an actuator api")
<b>actuator</b>

As we can see, actuator exposes a lot of endpoints , we’re just going to focus on prometheus endpoint.

![Alt text](./docs/service-actuator-prometheus.png?raw=true "Calling an actuator api")

