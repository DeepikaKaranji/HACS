package org.apache.kafka.common.securekafkastuff;
import org.apache.kafka.common.securekafkastuff.Topics;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;

import java.util.Properties;
import java.util.HashMap;

public class ConsumerTopic{

	public static HashMap<String,String> ConsumerTopic = new HashMap<String,String>();
	
	public static void PairConsumerTopic(String ConsumerGrp, String Topic){
		System.out.println("In Consumer Pair function");
		ConsumerTopic.put(ConsumerGrp,Topic);
		System.out.println("Consumer Grp: "+ConsumerTopic);
	}

	public Boolean TopicExists(String Topic){
		//recieve updated list of topics using topics.java
		Properties props_1 = new Properties();
		props_1.put("bootstrap.servers", "localhost:9092");

		Topics t = new Topics();
		HashMap<String,Boolean> TopicList = t.updateTopics(props_1);
		//create the consumer with the topic based on whether the topic exists. if it doesnt big F(so print error). update the (topic,consumergrp) pair
		Boolean TopicExist = false;
		if(TopicList.size()>0 & TopicList.get(Topic)){
			System.out.println("Topic");
			TopicExist = true;
		}
		return TopicExist;
	}
	
	
}
