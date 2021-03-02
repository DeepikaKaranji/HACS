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

public class producer_demo {
	
	private static final Logger logger = LogManager.getLogger(producer.class);
	
	//private String[] stocks = {"GOOGL","AMZN","AAPL","TSLA","TWTR"};
	
	
	public void sendInfo(KafkaProducer<String, encapsulator> Producer, String topicName, String Rule, String ConsumerGroup){
		/*
		for(int i = 0;i<100;i++) {
			int rnd = new Random().nextInt(stocks.length);
    			String key = stocks[rnd];
    			String value = Double.toString(new Random().nextDouble() * 1000.0);
    			
    			String Rule = SecMapObj.Maps.getJSONObject("rules")
    					.getJSONObject(topicName)
    					.getJSONArray(ConsumerGroup)
    					.getJSONObject(0).getString("Permission");
    					
    			encapsulator e = new encapsulator(Rule,value,"Stock Name,StockPrice");
    			Producer.send(new ProducerRecord<String, encapsulator>(topicName, key, e));
		}
		*/
		
		encapsulator e = new encapsulator(Rule,"156 256","BallNo TotalRun");
		Producer.send(new ProducerRecord<String, encapsulator>(topicName,"156 256", e));		
	}

	public static void main(String[] args) {
		
		System.out.println("Started Producer");
		String TopicName = "StockMarket";
		int count = 0;
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
		producer_demo p = new producer_demo();
		
		//SecureMapsAdmin SecMapObj = new SecureMapsAdmin();
		
		Vector<String> ConsumerGroupList = new Vector<String>();
		ConsumerGroupList.add("StockMarketGroup");
		if(ConsumerGroupList.size() == 0)
			System.out.println("ConsumerGroupList empty!");
		else
			System.out.println("All fine");
			
		for(String CgName : ConsumerGroupList){
			p.sendInfo(producer, TopicName, "READ", CgName);
		}
		producer.close();
	}	
}

