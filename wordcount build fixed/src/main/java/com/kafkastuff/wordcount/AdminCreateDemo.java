package com.kafkastuff.wordcount;
import org.apache.kafka.common.securekafkastuff.PunctAdmin;
import java.util.Properties;

public class AdminCreateDemo{
	public static void main(String[] args){
		PunctAdmin p = new PunctAdmin();
		String TopicName = "1Topic";
		Properties props_1 = new Properties();
		props_1.put("bootstrap.servers", "localhost:9092"); 
		
		//t.createTopic(TopicName,1,1,props_1);
		Boolean output = p.createTopic(TopicName,props_1);
		//Boolean output2 = p.deleteTopic(TopicName, props_1);
		
	}
}
