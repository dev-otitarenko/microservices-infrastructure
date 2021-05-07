# Monitoring with prometheus and grafana and log aggregation using ELK stack

1. [Support Prometheus in Spring Boot Application](./SB-PROMETHEUS.md)
2. [Configuring Logging to ELK in Spring Boot Application](./SB-LOGGING.md)
3. [Monitoring with Prometheus and Grafana](./PROMETHEUS.md)
4. [Log aggregation using ELK stack](./ELK.md)

## Installation the application

```sh
$ mvn clean package
$ docker-compose -f ./docker-compose-dev.yml stop
$ docker-compose -f ./docker-compose-dev.yml rm
$ docker-compose -f ./docker-compose-dev.yml up -d --build
```

or

```sh
$ sh ./build-dev.sh
```