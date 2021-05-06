# Best practice for microservices

## Monitoring microservices project

Start PROMETHEUS & GRAPHANA
```sh
$ docker-compose -f ./docker-compose-monitor.yml up -d
```

Stop the monitoring tools
```sh
$ docker-compose -f ./docker-compose-monitor.yml stop
```

Remove the monitoring tools
```sh
$ docker-compose -f ./docker-compose-monitor.yml rm
```