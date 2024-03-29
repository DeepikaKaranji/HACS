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

public class producer {
	
	private static final Logger logger = LogManager.getLogger(producer.class);
	
	private String[] runs = {"1","2","3","4","6"};
	
	/*
	public static void listGroups(Properties properties) {
		//LOG.info("Creating topic {}", topic);
		try (AdminClient adminClient = AdminClient.create(properties)) {
			ListConsumerGroupsResult listGroups = adminClient.listConsumerGroups();
			List<String> groupIds = listGroups.all().get().stream().map(s -> s.groupId()).collect(Collectors.toList()); 
		} catch (Exception e) {
			e.printStackTrace();
			//fail("Create test topic : " + topic + " failed, " + e.getMessage());
		}
	}
	*/
	
	
	public void sendInfo(KafkaProducer<String, encapsulator> Producer, String topicName, SecureMapsAdmin SecMapObj, String ConsumerGroup){
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
    			/*
			if (SecMapObj.CheckPermission(topicName, ConsumerGroup, ConsumerID, "100") == 0){
				Read readObj = new Read(key+": "+value);
				Producer.send(new ProducerRecord<String, Read>(topicName, key, readObj));
			}
			*/	
				
		}
	}

	public static void main(String[] args) {
		
		System.out.println("Started Producer");
		String TopicName = "Cricket";
		/*
		String TopicName = "StockMarketTopic";
		Properties props_1 = new Properties();
		props_1.put("bootstrap.servers", "localhost:9092"); 
		
		Topics t = new Topics();
		//t.updateTopics(props_1);
		t.createTopic(TopicName,1,1,props_1);
		*/
		//listGroups(props_1);
		int count = 0;
		/*
		while(Flag.flag!=true){
			count+=1;
			if(count==100){
				System.out.println(Flag.flag+" Busy waiting for setting rules!");
			}
		}
		*/
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");    
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);   
		props.put("linger.ms", 1);   
		props.put("buffer.memory", 33554432);
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		//props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		//props.put("value.serializer","org.apache.kafka.common.securekafkastuff.ReadSerializeroo");
		props.put("value.serializer","org.apache.kafka.common.securekafkastuff.encapSerializer");
		props.put("rules","GROUP1:READ");
		
		KafkaProducer<String, encapsulator> producer = new KafkaProducer<String, encapsulator>(props);
		//KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		producer p = new producer();

		/*
		SecureMaps SecMapObj = new SecureMaps();
		SecMapObj.AddTopic(TopicName);
		*/
		
		SecureMapsAdmin SecMapObj = new SecureMapsAdmin();
		
		
		//What is this? Isnt the ConsumerGroupList supposed to be taken from ConsumerTopic?
		//TODO: Connect this part of the code with ConsumerTopic asap.
		
		Vector<String> ConsumerGroupList = new Vector<String>();
		ConsumerGroupList.add("INDvsAUS");
		if(ConsumerGroupList.size() == 0)
			System.out.println("ConsumerGroupList empty!");
		else
			System.out.println("All fine");
			
			
		
		//TODO: ConsumerID needs to change because of single consumer group and consumer
		//String Consumer = "Consumer1";
		//Polling exists
		for(String CgName : ConsumerGroupList){
			//SecMapObj.AddConsumerGroup(TopicName, CgName);
			//SecMapObj.AddConsumer(TopicName, CgName, Consumer, "100", "");
			p.sendInfo(producer, TopicName, SecMapObj, CgName);
		}
		


		System.out.println("MESSAGE SENT");
		producer.close();
	}	
}

