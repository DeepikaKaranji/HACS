# Secure-Kafka-Storm

## Create a Topic via KafkaCLI
```
cd /usr/local/kafka
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic <TOPIC_NAME>
```
## Create a Consumer Group via KafkaCLI
```
cd /usr/local/kafka
bin/kafka-console-consumer.sh -bootstrap-server localhost:9092 -topic -group <group_name>
```

## PoC for Query on a Single Stream
```
Compilation: ./scripts/compile_code.sh
Create a Kafka topic: ./scripts/create_topic.sh
Set access control rule: .scripts/create_rule.sh
In 2 separate terminals:
  Producer: ./scripts/producer.sh 
  Consumer: ./scripts/consumer.sh
```

## PoC for Join Query on Two Streams
```
In 2 separate terminals:
  Producer: ./scripts/producerMultipleStreams.sh
  Consumer: ./scripts/spoutMultipleStreams.sh
```

## Compile and Execute a Particular File
```
mvn compile
mvn -e -X exec:java -Dexec.mainClass=<path_to_file>
```

## mvn Process PID Command
```
ps -o pid,user,cmd -C java | sed -e 's/\([0-9]\+ *[^ ]*\) *[^ ]* *\([^$]*\)/\1 \2/' -e 's/-c[^ ]* [^ ]* \|-[^ ]* //g'
```

## Running log4j for Vanilla Kafka with Maven

```
mvn exec:java -Dlog4j.configurationFile=src/main/resources/log4j2.xml -Dexec.mainClass=com.kafkastuff.wordcount.producer
```
To get additional details, modifiy the level field of ```<Root>``` with "trace", "debug", "info", "warn", "error" or "fatal" in ```log4j2.xml```

Note: Zookeeper and Kafka Server have to run in the background during execution.
