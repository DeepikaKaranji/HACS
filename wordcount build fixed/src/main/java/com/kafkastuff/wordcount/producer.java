package com.kafkastuff.wordcount;
import java.util.Properties;
import java.util.Collections;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

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


public class producer {
	
	private static final Logger logger = LogManager.getLogger(producer.class);
	
	public static void listGroups(Properties properties) {
		//LOG.info("Creating topic {}", topic);
		try (AdminClient adminClient = AdminClient.create(properties)) {
			ListConsumerGroupsResult listGroups = adminClient.listConsumerGroups();
			List<String> groupIds = listGroups.all().get().stream().map(s -> s.groupId()).collect(Collectors.toList()); 
			System.out.println("Group IDs: "+groupIds);
		} catch (Exception e) {
			e.printStackTrace();
			//fail("Create test topic : " + topic + " failed, " + e.getMessage());
		}
	}
	
	
	public void sendInfo(KafkaProducer<String,String> Producer,String topicName){
		for(int i = 0;i<100;i++) {
			String[] updatedKV = Producer.updateRules("KEY" + Integer.toString(i), "VALUE" + Integer.toString(i));
			Producer.send(new ProducerRecord<String, String>(topicName,updatedKV[0],updatedKV[1]));
		}	
	}

	public static void main(String[] args) {
		//String topicName = "my-first-topic";
		
		System.out.println("Started Producer");
		String topicName = "StockMarketTopic";
		Properties props_1 = new Properties();
		props_1.put("bootstrap.servers", "localhost:9092"); 
		//createTestTopic(topicName,1,1,props_1);
		Topics t = new Topics();
		//t.updateTopics(props_1);
		t.createTopic(topicName,1,1,props_1);
		
		//listGroups(props_1);

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");    
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);   
		props.put("linger.ms", 1);   
		props.put("buffer.memory", 33554432);
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("rules","GROUP1:READ");
		
		KafkaProducer<String,String> producer = new KafkaProducer<String,String>(props);
		producer p = new producer();
		System.out.println("Before Send");
		p.sendInfo(producer,topicName);
		System.out.println("MESSAGE SENT");
		producer.close();
	}	
}

