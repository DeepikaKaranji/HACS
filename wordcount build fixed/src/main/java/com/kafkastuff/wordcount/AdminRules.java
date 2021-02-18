/*
- lets us list consumer groups and topics
- rules are not updated second by second


- insertRules(consumergrp,topic,rule)
	list consumergrp = update
	list topic = update

	if(topic exists and consumergrp exists){
		insert rule in JSON 
	}
	-lazy update of topics and consumer
	
- deleteRules
	list consumergrp
	list topic
	
	if(topic exists and consumergrp exists){
		delete rule in JSON
	}

	-lazy update of topics and consumer groups
	
	-session terminates then current mapping of topics and groups dies
	
	topic and conusmer grp on terminal=>admin(alive)=>producer(terminates)
	
	persistent storage => eventuality => file
	
	JSON has to be persistent
	
	
*/

package com.kafkastuff.wordcount;
import org.apache.kafka.common.securekafkastuff.ConsumerTopic;
import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.common.securekafkastuff.Topics;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import org.apache.kafka.common.securekafkastuff.SecureMapsAdmin;
import com.kafkastuff.wordcount.Flag;

public class AdminRules extends SecureMapsAdmin{

	SecureMapsAdmin admin = new SecureMapsAdmin();
	Properties properties = new Properties();

	AdminRules(){
		properties.put("bootstrap.servers", "localhost:9092,localhost:9093");
		properties.put("security.protocol","SSL");
		properties.put("ssl.truststore.location","/usr/local/etc/kafka/kafka.truststore.jks");
		properties.put("ssl.truststore.password","kafka123");
		properties.put("ssl.keystore.location","/usr/local/etc/kafka/client.keystore.jks");
		properties.put("ssl.keystore.password","kafka123");
		properties.put("ssl.key.password","kafka123");
		properties.put("ssl.client.auth", "required");
		properties.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
		properties.put("ssl.endpoint.identification.algorithm", "");
	}

	private Boolean listGroups(String ConsumerGrp) {
		//LOG.info("Creating topic {}", topic);
		Boolean exist = false;
		try (AdminClient adminClient = AdminClient.create(properties)) {
			ListConsumerGroupsResult listGroups = adminClient.listConsumerGroups();
			List<String> groupIds = listGroups.all().get().stream().map(s -> s.groupId()).collect(Collectors.toList());
			//System.out.println("Groups: "+groupIds);
			if(groupIds.contains(ConsumerGrp)){
				exist = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//fail("Create test topic : " + topic + " failed, " + e.getMessage());
		}
		return exist;
	}


	public Boolean insertRules(String consumerGrp, String TopicName, String Rule){
		Boolean output = false;
		//SecureMaps s = new SecureMaps();
		
		if(TopicExists(TopicName) && listGroups(consumerGrp)){
			//if(!admin.GetTopics().contains(TopicName)){
			if(!super.GetTopics().contains(TopicName)){
				//int x = admin.AddTopic(TopicName);
				//int y = admin.AddConsumerGroup(TopicName,consumerGrp);
				int x = super.AddTopic(TopicName);
				int y = super.AddConsumerGroup(TopicName,consumerGrp);
			}
			// else if(!admin.GetConsumerGroups(TopicName).contains(consumerGrp)){
			// 	int y = admin.AddConsumerGroup(TopicName,consumerGrp);
			// }			
			else if(!super.GetConsumerGroups(TopicName).contains(consumerGrp)){
				int y = super.AddConsumerGroup(TopicName,consumerGrp);
			}
			
			// int x = admin.AddRuleAdmin(TopicName, consumerGrp,Rule);
			// System.out.println("JSON is: "+admin.GetMaps()+" Add rule: "+x);
			int x = super.AddRuleAdmin(TopicName, consumerGrp,Rule);
			System.out.println("JSON is: "+super.GetMaps()+" Add rule: "+x);
			output = true;
		}
		return output;
		
	}
	
	public Boolean deleteRules(String consumerGrp, String TopicName, String Rule){
		Boolean output = false;
		//SecureMaps s = new SecureMaps();
		
		if(TopicExists(TopicName) && listGroups(consumerGrp)){
		
			System.out.println("Topic exists: "+super.GetTopics().contains(TopicName));
			System.out.println("Consumer grp exists: "+super.GetConsumerGroups(TopicName).contains(consumerGrp));
			System.out.println("Permission exists: "+super.CheckPermissionAdmin(TopicName,consumerGrp,Rule));
			if(super.GetTopics().contains(TopicName)
			 && super.GetConsumerGroups(TopicName).contains(consumerGrp)
			 && super.CheckPermissionAdmin(TopicName,consumerGrp,Rule)){
			 	
			 	int y = super.AddRuleAdmin(TopicName, consumerGrp,"");
			 	System.out.println("Rule insertion for delete: "+y);
			 }
			output = true;
		}
		System.out.println("JSON is: "+super.GetMaps());
		return output;
	}

	private Boolean TopicExists(String Topic){
		//recieve updated list of topics using topics.java
		Topics t = new Topics();
		HashMap<String,Boolean> TopicList = t.updateTopics(properties);
		//create the consumer with the topic based on whether the topic exists. if it doesnt big F(so print error). update the (topic,consumergrp) pair
		Boolean TopicExist = false;
		if(TopicList.size()>0 & TopicList.get(Topic)){
			System.out.println("Topic");
			TopicExist = true;
		}
		return TopicExist;
	}
	
	public static void main(String[] args){
		AdminRules a = new AdminRules();
		
		System.out.println("Insert: "+a.insertRules("StockMarketGroup","StockMarket","READ"));
		System.out.println("Insert: "+a.insertRules("abc","xyz","READ"));
		//System.out.println("Delete: "+a.deleteRules("StockMarketGroup","StockMarket","READ"));
		
	}
}
