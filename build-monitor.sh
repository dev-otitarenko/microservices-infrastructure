#!/usr/bin/env bash

docker-compose -f docker-compose-monitor.yml -p ms-practice stop
docker-compose -f docker-compose-monitor.yml -p ms-practice rm
docker-compose -f docker-compose-monitor.yml -p ms-practice up -d --build
