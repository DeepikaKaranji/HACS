cd ../wordcount\ build\ fixed
mvn -e -X exec:java -Dexec.mainClass=com.kafkastuff.wordcount.spoutMultipleStreams.sh
