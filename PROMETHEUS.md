# Monitoring with Prometheus and Grafana

![Alt text](./docs/prometheus-1.gif?raw=true "Prometheus General")

As for now everything looks fine, but while working with such a complex architecture 
we need to keep an eye on our system in case of a any potential failure.
To do so , in this part we’re going to learn how to monitor, trace and observe the behaviour 
of our microservices using prometheus and grafana.

### Installing Prometheus/Grafana

Start Prometheus/Grafana:
```sh
$ docker-compose -f ./docker-compose-monitor.yml up -d
```

Stop Prometheus/Grafana:
```sh
$ docker-compose -f ./docker-compose-monitor.yml stop
```

Remove Prometheus/Grafana:
```sh
$ docker-compose -f ./docker-compose-monitor.yml rm
```

## What is prometheus ?

Prometheus is an open source time-series database originally developed by SoundCloud. 
It comes with its proper query language PromQL and used for event monitoring and alerting.

### Running prometheus

Navigate to http://localhost:9090 and explore Prometheus. Once all our microservices are up
we should have something like that:

![Alt text](./docs/prometheus-2.png?raw=true "Prometheus 2")

We can now explore all the metrics in prometheus and keep an eye on the behaviour of
our microservices, this example shows the system’s CPU usage of our microservices:

![Alt text](./docs/prometheus-3.png?raw=true "Prometheus 3")

## Grafana
So far our microservices are exposing the actautor’s endpoint into prometheus server 
which is so interesting , but when we have this amount of information we need another system 
to help us better visualize , analyse our microservices.

Grafana is open source visualization and analytics software. It allows you to query, visualize,
alert on, and explore your metrics no matter where they are stored. In plain English, it provides 
you with tools to turn your time-series database (prometheus for example) data into beautiful graphs
and visualizations.

### Running Grafana

Grafana run by default on port 3000, navigate to http://localhost:3000 and log in to Grafana 
with the default username admin and password admin.

![Alt text](./docs/graphana-1.png?raw=true "Grafana 1")

### Configuring Grafana to import metrics data from Prometheus

Like we said before grafana imports metrics from time series database in order to visualize them, 
to do we need to configure grafana to import these metrics from the prometheus server.
the following image shows how grafana and prometheus interact with each other:

![Alt text](./docs/graphana-2.png?raw=true "Grafana 2")

To import prometheus metrics into Grafana we have to follow these steps:

1. Add the Prometheus data source in Grafana
   go to configuration/datasource and click to add new datasource

![Alt text](./docs/graphana-3.png?raw=true "Grafana 3")

Grafana provides a lot of databases , we just need to select Prometheus
After selecting your TSDB , add the following configuration to connect garafana with your prometheus server:

![Alt text](./docs/graphana-4.png?raw=true "Grafana 4")

### 2.Create a new dashboard

Select the (+) icon form the left menu and click dashboard:

![Alt text](./docs/graphana-5.png?raw=true "Grafana 5")

![Alt text](./docs/graphana-6.png?raw=true "Grafana 6")

### 3.Visualize your dashboard

![Alt text](./docs/graphana-7.png?raw=true "Grafana 7")

### Importing pre-built dashboards
Grafana.com maintains a collection of shared dashboards which can be downloaded and used with standalone instances of Grafana.
Use the Grafana.com “Filter” option to browse dashboards for the “Prometheus” data source only.
We’re going to search for the spring boot statistics dashboard for example:

![Alt text](./docs/graphana-8.png?raw=true "Grafana 8")

copy the id and go to dashboard/import and click load

![Alt text](./docs/graphana-9.png?raw=true "Grafana 9")

Your dashboard is ready to explore. Now it’s up to you and your needs , you can do that whatever you want, and you can keep 
and eye on your whole system. 

