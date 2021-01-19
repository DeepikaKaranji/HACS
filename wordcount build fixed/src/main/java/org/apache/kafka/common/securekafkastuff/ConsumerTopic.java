package org.apache.kafka.common.securekafkastuff;
import org.apache.kafka.common.securekafkastuff.Topics;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import java.util.Properties;
import java.util.Vector;
import java.util.HashMap;

public class ConsumerTopic{

	public static HashMap<String,Vector<String>> ConsumerTopicMap = new HashMap<String,Vector<String>>();
	
	public static void PairConsumerTopic(String ConsumerGrp, String Topic){
		System.out.println("In Consumer Pair function");
		/*Vector<String> v = ConsumerTopicMap.get(Topic);
		v.add(ConsumerGrp);
		ConsumerTopicMap.put(Topic,v);*/
		// if(ConsumerTopicMap.size() == 0)
			// System.out.println("Hashmap is empty\n");
		if(ConsumerTopicMap.containsKey(Topic))
		{
			Vector<String> v = ConsumerTopicMap.get(Topic);
			v.add(ConsumerGrp);
			ConsumerTopicMap.put(Topic,v);
			System.out.println("ConsumerTopicMap was not empty\n");
		}
		else
		{
			Vector<String> CGroup = new Vector<String>();
			CGroup.add(ConsumerGrp);
			ConsumerTopicMap.put(Topic, CGroup);
			System.out.println("ConsumerTopicMap was empty and handled\n");
		}
		System.out.println("Consumer Pair function execution finished\n");
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
