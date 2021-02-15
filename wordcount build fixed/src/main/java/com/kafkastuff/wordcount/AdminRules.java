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
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import org.apache.kafka.common.securekafkastuff.SecureMapsAdmin;
import com.kafkastuff.wordcount.Flag;

public class AdminRules{

	SecureMapsAdmin admin = new SecureMapsAdmin();

	public Boolean listGroups(String ConsumerGrp) {
		//LOG.info("Creating topic {}", topic);
		Boolean exist = false;
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092"); 
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
		ConsumerTopic c = new ConsumerTopic();
		//SecureMaps s = new SecureMaps();
		
		if(c.TopicExists(TopicName) && listGroups(consumerGrp)){
			if(!admin.GetTopics().contains(TopicName)){
				int x = admin.AddTopic(TopicName);
				int y = admin.AddConsumerGroup(TopicName,consumerGrp);
			}
			else if(!admin.GetConsumerGroups(TopicName).contains(consumerGrp)){
				int y = admin.AddConsumerGroup(TopicName,consumerGrp);
			}
			
			int x = admin.AddRuleAdmin(TopicName, consumerGrp,Rule);
			System.out.println("JSON is: "+admin.GetMaps()+" Add rule: "+x);
			output = true;
		}
		return output;
		
	}
	
	public Boolean deleteRules(String consumerGrp, String TopicName, String Rule){
		Boolean output = false;
		ConsumerTopic c = new ConsumerTopic();
		//SecureMaps s = new SecureMaps();
		
		if(c.TopicExists(TopicName) && listGroups(consumerGrp)){
		
			System.out.println("Topic exists: "+admin.GetTopics().contains(TopicName));
			System.out.println("Consumer grp exists: "+admin.GetConsumerGroups(TopicName).contains(consumerGrp));
			System.out.println("Permission exists: "+admin.CheckPermissionAdmin(TopicName,consumerGrp,Rule));
			if(admin.GetTopics().contains(TopicName)
			 && admin.GetConsumerGroups(TopicName).contains(consumerGrp)
			 && admin.CheckPermissionAdmin(TopicName,consumerGrp,Rule)){
			 	
			 	int y = admin.AddRuleAdmin(TopicName, consumerGrp,"");
			 	System.out.println("Rule insertion for delete: "+y);
			 }
			output = true;
		}
		System.out.println("JSON is: "+admin.GetMaps());
		return output;
	}
	
	public static void main(String[] args){
		AdminRules a = new AdminRules();
		
		System.out.println("Insert: "+a.insertRules("StockMarketGroup","StockMarket","READ"));
		//System.out.println("Delete: "+a.deleteRules("StockMarketGroup","StockMarket","READ"));
		
	}
}
