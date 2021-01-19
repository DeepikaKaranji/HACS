package org.apache.kafka.common.securekafkastuff;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.HashMap;

public class Topics{


	public HashMap<String,Boolean> TopicList = new HashMap<String,Boolean>();
	
	
	public HashMap<String, Boolean> updateTopics(Properties properties){
		try (AdminClient adminClient = AdminClient.create(properties)) {
			ListTopicsResult listTopics = adminClient.listTopics();
			Set<String> names = listTopics.names().get();
			for (String s:names){
				TopicList.put(s, true);
			}
			System.out.println("Topic list is: "+TopicList);
		} catch (Exception e) {
			e.printStackTrace();
			//fail("Create test topic : " + topic + " failed, " + e.getMessage());
		}
		return TopicList;
	}
	
	public void createTopic(String topic, int numberOfPartitions, int replicationFactor, Properties properties) {
		try (AdminClient adminClient = AdminClient.create(properties)) {
			ListTopicsResult listTopics = adminClient.listTopics();
			Set<String> names = listTopics.names().get();
			System.out.println("names are: "+names);
			boolean contains = names.contains(topic);
			if(!contains){
				NewTopic topicObj = new NewTopic(topic, numberOfPartitions, (short) replicationFactor);
				adminClient.createTopics(Collections.singleton(topicObj)).all().get();
				TopicList.put(topic,true);
				System.out.println("Added "+topic+" in "+TopicList);
			}
			else{
				System.out.println("==============Topic already existing==============");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//fail("Create test topic : " + topic + " failed, " + e.getMessage());
		}
	}
	
	public void deleteTopic(String topic,Properties properties){
		try (AdminClient adminClient = AdminClient.create(properties)) {
			ListTopicsResult listTopics = adminClient.listTopics();
			Set<String> names = listTopics.names().get();
			boolean contains = names.contains(topic);
			if(contains){
				//NewTopic topicObj = new NewTopic(topic, numberOfPartitions, (short) replicationFactor);
				adminClient.deleteTopics(Collections.singleton(topic));
				TopicList.put(topic,false);
				System.out.println("Removed "+topic+" in "+TopicList);
			}
			else{
				System.out.println("==============Topic not existing==============");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//fail("Create test topic : " + topic + " failed, " + e.getMessage());
		}		
	}
	
	
}
