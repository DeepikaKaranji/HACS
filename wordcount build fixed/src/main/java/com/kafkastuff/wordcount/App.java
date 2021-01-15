
/*
package com.kafkastuff.wordcount;

import org.apache.storm.Config;
import java.util.UUID;
import org.apache.storm.spout.SchemeAsMultiScheme;
//import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import java.lang.InterruptedException;
import java.lang.Exception;
import java.util.Properties;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.thrift.TException;
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import com.kafkastuff.wordcount.splitter;




public class App 
{
    public static void main( String[] args ) throws AlreadyAliveException, InterruptedException, org.apache.storm.thrift.TException, java.lang.Exception
    {
        System.out.println( "TOPOLOGY_STARTING" );
	System.out.println("Config given for topology");
	String zkConnString = "localhost:2181";
	System.out.println("Config given for Spout");
	final TopologyBuilder tp = new TopologyBuilder();
	System.out.println("Empty Topology created");
	KafkaSpoutConfig<String,String> kafkaSpoutConfig = KafkaSpoutConfig.builder("localhost:9092","TestTopic")
	.setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafka_spout-" + UUID.randomUUID().toString())
	.build();
	KafkaSpout<String,String> kafkaSpoutInput = new KafkaSpout<>(kafkaSpoutConfig);
	tp.setSpout("kafka_spout",kafkaSpoutInput, 1);
	tp.setBolt("sentence-splitter", new splitter(), 1).shuffleGrouping("kafka_spout");
	System.out.println("Spout set");
	LocalCluster localCluster = new LocalCluster();
	localCluster.submitTopology("stupidtops",new Config(),tp.createTopology());
	Thread.sleep(30000);
    }
}
*/
// /home/dpk/Desktop/Dpk/SEM7/CAPSTONE1/Secure-Kafka-Storm/wordcount build fixed/src/main/java/org/apache/kafka/common/securekafkastuff/SecureMaps.java
package com.kafkastuff.wordcount;
import org.apache.kafka.common.securekafkastuff.SecureMaps;
import org.apache.storm.Config;
import java.util.UUID;
import org.apache.storm.spout.SchemeAsMultiScheme;
//import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import java.lang.InterruptedException;
import java.lang.Exception;
import java.util.Properties;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.thrift.TException;
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import com.kafkastuff.wordcount.splitter;
import org.apache.kafka.common.securekafkastuff.ConsumerTopic;


public class App 
{
    public static void main( String[] args ) throws AlreadyAliveException, InterruptedException, org.apache.storm.thrift.TException, java.lang.Exception
    {
        System.out.println( "TOPOLOGY_STARTING" );


	//Create map

	SecureMaps SecMapObj = new SecureMaps();
	SecMapObj.AddTopic("Topic1");
	SecMapObj.AddConsumerGroup("Topic1", "CG1");
	SecMapObj.AddConsumerGroup("Topic1", "CG2");
	SecMapObj.AddConsumer("Topic1", "CG1", "CON1", "111", "*");
	SecMapObj.AddConsumer("Topic1", "CG1", "CON2", "222", "**");
	SecMapObj.AddConsumer("Topic1", "CG2", "CON1", "111", "*");
	SecMapObj.AddConsumer("Topic1", "CG2", "CON2", "222", "**");
	SecMapObj.UpdateConsumerPermission("Topic1", "CG1", "CON1", "333");
	SecMapObj.UpdateConsumerHostIP("Topic1", "CG1", "CON1", "***");
	SecMapObj.UpdateConsumerPermission("Topic1", "CG1", "CON2", "69");
	SecMapObj.UpdateConsumerHostIP("Topic1", "CG1", "CON2", "*********************************************");

	SecMapObj.AddTopic("Topic2");
	SecMapObj.AddConsumerGroup("Topic2", "CG1");
	SecMapObj.AddConsumerGroup("Topic2", "CG2");
	SecMapObj.AddConsumer("Topic2", "CG1", "CON1", "111", "*");
	SecMapObj.AddConsumer("Topic2", "CG1", "CON2", "222", "**");
	SecMapObj.AddConsumer("Topic2", "CG2", "CON1", "111", "*");
	SecMapObj.AddConsumer("Topic2", "CG2", "CON2", "222", "**");
	SecMapObj.UpdateConsumerPermission("Topic2", "CG1", "CON1", "333");
	SecMapObj.UpdateConsumerHostIP("Topic2", "CG1", "CON1", "***");
	SecMapObj.UpdateConsumerPermission("Topic2", "CG1", "CON2", "69");
	SecMapObj.UpdateConsumerHostIP("Topic2", "CG1", "CON2", "*********************************************");

	System.out.println(SecMapObj.GetMaps());
	
	System.out.println("Config given for topology"); 
	//Kafka Spout configerations

	System.out.println("Config given for topology");

	String zkConnString = "localhost:2181";
	System.out.println("Config given for Spout");
	final TopologyBuilder tp = new TopologyBuilder();
	System.out.println("Empty Topology created");
	String ConsumerGrp = "StockMarketConsumer"+UUID.randomUUID().toString();
	String Topic = "StockMarketTopic";
	
	
	ConsumerTopic c = new ConsumerTopic();
	//System.out.println("Topic exists: "+c.ConsumerTopicPair(ConsumerGrp,Topic));
	if(c.TopicExists(Topic)){
		KafkaSpoutConfig<String,String> kafkaSpoutConfig = KafkaSpoutConfig.builder("localhost:9092",Topic)
		.setProp(ConsumerConfig.GROUP_ID_CONFIG, ConsumerGrp)
		.build();
		KafkaSpout<String,String> kafkaSpoutInput = new KafkaSpout<>(kafkaSpoutConfig);

		ConsumerTopic.PairConsumerTopic(ConsumerGrp,Topic);
		
		tp.setSpout("kafka_spout",kafkaSpoutInput, 1);
		tp.setBolt("sentence-splitter", new splitter(), 1).shuffleGrouping("kafka_spout");
		System.out.println("Spout set");
		
		ConsumerTopic c1 = new ConsumerTopic();
		System.out.println("Verification of static: "+c1.ConsumerTopic);

		//NIMBUS ERRORS HERE
		LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("stupidtops",new Config(),tp.createTopology());
		Thread.sleep(30000);
	}
	else{
		System.out.println("Topic doesnt exist!!");
	}
    }

}
