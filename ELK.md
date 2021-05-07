# Log aggregation using ELK stack

In this part we’re gonna focus on the Log aggregation and analysis.
## Why it is so important ?
Logs are essential information, allowing us to better understand, analyze and diagnose certain malfunctions, as they can help us follow and trace the different actions carried out by the actors of our system. Only, it becomes very complex to exploit this critical data with so many applications, equipment, services, systems and managed elements in an information system.

![Alt text](./docs/logs-analise.png?raw=true "Log analysis")
<b>Picture-1: Log analysis</b>

As a solution for this problem, we’re gonna use ELK stack, one of the best and efficient tool for log aggregation and analysis.

## So what is Elk stack ?
Composed of Elasticsearch, Logstash and Kibana, ELK is a suite of three complementary open source projects. This trio of tools will make it possible to display an overview to the system administrator while offering him a quick graphical analysis of the elements he is in charge of.

![Alt text](./docs/elk.png?raw=true "ELK")
<b>Picture-2: ELK</b>

<b>1-Elasticsearch</b>: Elasticsearch is a real-time distributed and open source full-text search and analytics engine. It is accessible from RESTful web service interface and uses JSON documents to store data. It is built on Java programming language and hence Elasticsearch can run on different platforms. It enables users to explore very large amount of data at very high speed.

<b>2-Logstash</b>: Back then, logstash has been used to process logs from applications and send them to Elasticsearch. But , since then logstash has evolved into more general purpose tool that makes it a data processing pipeline.
The data collected by logstash will be processed and shipped of to one or more destinations (Elasticsearch, kafka, e-mail…).

<b>3-Kibana</b>: Kibana is basically an analytics and visualization platform, which lets you easily visualize data from Elasticsearch and analyze it to make sense of it. You can assume Kibana as an Elasticsearch dashboard where you can create visualizations such as pie charts, line charts, and many others.

## Global Architecture
The following is the architecture of ELK Stack which shows the proper order of log flow within ELK. Here, the logs generated from various sources are collected and processed by Logstash, based on the provided filter criteria. Logstash then pipes those logs to Elasticsearch which then analyzes and searches the data. Finally, using Kibana, the logs are visualized and managed as per the requirements.

![Alt text](./docs/elk-architecture.png?raw=true "elk architecture")
<b>elk architecture</b>

## ELK with microservices
All our microservices will push their respective logs to Logstash, which will use Elasticsearch to index them. The indexed logs can be consumed later by Kibana.
We will use Logback as a logging framework.
Logback offers a faster implementation , provides more options for configuration, and more flexibility in archiving old log files.

![Alt text](./docs/elk-logback.png?raw=true "elk logback")

## Installation ELK (folder ./elk)

Start ELK:
```sh
$ docker-compose -f ./docker-compose.yml up -d --build
```

Stop ELK:
```sh
$ docker-compose -f ./docker-compose.yml stop
```

Remove ELK:
```sh
$ docker-compose -f ./docker-compose.yml rm
```