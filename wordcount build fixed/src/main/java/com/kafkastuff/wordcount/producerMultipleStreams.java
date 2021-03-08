package com.kafkastuff.wordcount;
import java.util.Properties;
import java.util.Collections;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.kafka.common.securekafkastuff.ConsumerTopic;
import org.apache.kafka.common.securekafkastuff.SecureMapsAdmin;
import java.util.Vector;


import org.apache.kafka.clients.producer.Producer;
//import com.kafkastuff.wordcount.KafkaProducer;
//import com.kafkastuff.wordcount.Producer_kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.common.securekafkastuff.Topics;
import org.apache.kafka.common.securekafkastuff.ReadSerializer;
import org.apache.kafka.common.securekafkastuff.ReadDeserializer;
import org.apache.kafka.common.securekafkastuff.Read;
import org.json.*;
import java.util.Random;
import org.apache.kafka.common.securekafkastuff.encapsulator;
import com.kafkastuff.wordcount.Flag;

public class producerMultipleStreams {
	
	private static final Logger logger = LogManager.getLogger(producer.class);
	
	private String[] runs = {"1","2","3","4","6"};
	
	public void sendInfo(KafkaProducer<String, encapsulator> Producer, String topicName1,String topicName2, String ConsumerGroup){
		/*
		for(int i = 0;i<300;i++) {
			int rnd = new Random().nextInt(runs.length);
    			String value = runs[rnd];
				String key = String.valueOf(i + 1);
				String KV = key + "," + value;
    			
    			String Rule = SecMapObj.Maps.getJSONObject("rules")
    					.getJSONObject(topicName)
    					.getJSONArray(ConsumerGroup)
    					.getJSONObject(0).getString("Permission");
    					
    			encapsulator e = new encapsulator(Rule,KV,"BallNumber,Score");
    			Producer.send(new ProducerRecord<String, encapsulator>(topicName, key, e));
				
		}
		*/
		
		encapsulator e1 = new encapsulator("READ:a,b","1,2,3,5","a,b,c,e");
		encapsulator e2 = new encapsulator("READ:a,e","1,4,5","a,d,e"); //1 2 5
		
		Producer.send(new ProducerRecord<String, encapsulator>(topicName1, "1,2,3", e1));
		Producer.send(new ProducerRecord<String, encapsulator>(topicName2, "4,5,6", e2));
	}

	public static void main(String[] args) {
		
		System.out.println("Started Producer");
		String TopicName1 = "t1";
		String TopicName2 = "t2";
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");    
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);   
		props.put("linger.ms", 1);   
		props.put("buffer.memory", 33554432);
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer","org.apache.kafka.common.securekafkastuff.encapSerializer");
		props.put("rules","GROUP1:READ");
		
		KafkaProducer<String, encapsulator> producer = new KafkaProducer<String, encapsulator>(props);
		producerMultipleStreams p = new producerMultipleStreams();

		//SecureMapsAdmin SecMapObj = new SecureMapsAdmin();
		Vector<String> ConsumerGroupList = new Vector<String>();
		ConsumerGroupList.add("cgrp");
		if(ConsumerGroupList.size() == 0)
			System.out.println("ConsumerGroupList empty!");
		else
			System.out.println("All fine");
		
		
		for(String CgName : ConsumerGroupList){
			p.sendInfo(producer, TopicName1,TopicName2, CgName);
		}
		


		System.out.println("MESSAGE SENT");
		producer.close();
	}	
}

