#!/usr/bin/env bash

cd ./elk
# run services
docker-compose -f docker-compose.yml -p ms-practice stop
docker-compose -f docker-compose.yml -p ms-practice rm
docker-compose -f docker-compose.yml -p ms-practice up -d --build

cd ../