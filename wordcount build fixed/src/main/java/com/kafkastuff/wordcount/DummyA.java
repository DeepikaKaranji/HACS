package com.kafkastuff.wordcount;

import com.kafkastuff.wordcount.Container;
import org.apache.kafka.common.securekafkastuff.ConsumerTopic;
import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import org.apache.kafka.common.securekafkastuff.SecureMapsAdmin;
import com.kafkastuff.wordcount.Flag;


public class DummyA{
	public static void main(String[] args) throws InterruptedException{
		String ConsumerGrp = "StockMarketGroup";
		Boolean exist = false;
		Properties properties = new Properties();


		properties.put("bootstrap.servers", "localhost:9092,localhost:9093");
		//properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//properties.put("group.id","test2");
		properties.put("security.protocol","SSL");
		properties.put("ssl.truststore.location","/usr/local/etc/kafka/kafka.truststore.jks");
		properties.put("ssl.truststore.password","kafka123");
		properties.put("ssl.keystore.location","/usr/local/etc/kafka/client.keystore.jks");
		properties.put("ssl.keystore.password","kafka123");
		properties.put("ssl.key.password","kafka123");
		properties.put("ssl.client.auth", "required");
		properties.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
		properties.put("ssl.endpoint.identification.algorithm", "");

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

		System.out.println("Exist: "+exist);
	}
}
