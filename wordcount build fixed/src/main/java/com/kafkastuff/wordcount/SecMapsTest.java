package com.kafkastuff.wordcount;
import org.apache.kafka.common.securekafkastuff.SecureMapsAdmin;

public class SecMapsTest{
	public static void main(String[] args){
		SecureMapsAdmin s = new SecureMapsAdmin();
		//Test Cases
		//s.AddTopic("GEI");
		//s.AddConsumerGroup("GEI","Hoes");
		//s.AddRuleAdmin("GEI","Hoes","Bitch you get no permissions");
		//s.AddRuleAdmin("GEI","Hoes","permissions granted");
		//s.AddConsumerGroup("GEI","PPL");
		//s.AddRuleAdmin("GEI","HELLO","permissions granted");
		//s.AddRuleAdmin("G","H","wrong perm!");
		//s.AddRuleAdmin("G","Hoes","wrong perm!");
		//s.AddRuleAdmin("","Hoes","wrong perm!");
		
		//s.AddTopic("StockMarketTopic");
		//s.AddConsumerGroup("StockMarketTopic","StockMarket");
		
		s.GetTopics();
		//s.ReadJSONFile();
		//s.WriteJSONFile();
	}
}
