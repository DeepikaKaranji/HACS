# Secure-Kafka-Storm

## Create a Topic

```
cd /usr/local/kafka
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic <TOPIC_NAME>
```

## mvn Execution
```
mvn compile
mvn -e -X exec:java -Dexec.mainClass=com.kafkastuff.wordcount.App
```

## mvn Process PID command
```
ps -o pid,user,cmd -C java | sed -e 's/\([0-9]\+ *[^ ]*\) *[^ ]* *\([^$]*\)/\1 \2/' -e 's/-c[^ ]* [^ ]* \|-[^ ]* //g'
```

## running log4j

```
mvn exec:java -Dlog4j.configurationFile=src/main/resources/log4j2.xml -Dexec.mainClass=com.kafkastuff.wordcount.producer
```

To get additional details, modifiy the level field of ```<Root>``` with "trace", "debug", "info", "warn", "error" or "fatal" in ```log4j2.xml```
