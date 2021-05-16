## Running the application

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

## Removing the application

```sh
$ docker-compose -f ./docker-compose-dev.yml stop
$ docker-compose -f ./docker-compose-dev.yml rm
```