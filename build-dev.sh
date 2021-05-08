#!/usr/bin/env bash

# remove all old unused images
docker rmi $(docker images -a -q)

cd ./ms-department-service
# compile
mvn clean package -Dmaven.test.skip=true

cd ../

# run services
docker-compose -f docker-compose-dev.yml -p ms-practice stop
docker-compose -f docker-compose-dev.yml -p ms-practice rm
docker-compose -f docker-compose-dev.yml -p ms-practice up -d --build