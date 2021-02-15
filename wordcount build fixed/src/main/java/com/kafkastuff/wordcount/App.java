package com.kafkastuff.wordcount;
import org.apache.kafka.common.securekafkastuff.SecureMaps;
//import org.apache.kafka.common.securekafkastuff.Read;
import org.apache.storm.Config;
import java.util.UUID;
import java.util.Vector;

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
import org.apache.storm.kafka.spout.KafkaSpoutConfig.Builder;

public class App 
{
    public static void main( String[] args ) throws AlreadyAliveException, InterruptedException, org.apache.storm.thrift.TException, java.lang.Exception
    {
        	System.out.println( "TOPOLOGY_STARTING" );		
		System.out.println("Config given for topology"); 

		System.out.println("Config given for topology");

		String zkConnString = "localhost:2181";
		System.out.println("Config given for Spout");
		final TopologyBuilder tp = new TopologyBuilder();
		System.out.println("Empty Topology created");
//		String ConsumerGrp = "StockMarketConsumer"+UUID.randomUUID().toString();

		String ConsumerGrp = "StockMarketGroup";
		String Topic = "StockMarket";
		
		
		ConsumerTopic c = new ConsumerTopic();
		//System.out.println("Topic exists: "+c.ConsumerTopicPair(ConsumerGrp,Topic));
		//Poll
		if(c.TopicExists(Topic)){
			KafkaSpoutConfig<String, String> kafkaSpoutConfig = KafkaSpoutConfig.builder("localhost:9092",Topic)
			.setProp(ConsumerConfig.GROUP_ID_CONFIG, ConsumerGrp)
			.setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
			//.setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.securekafkastuff.ReadDeserializeroo")	
			.setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.securekafkastuff.encapDeserializer")
			.build();
			KafkaSpout<String,String> kafkaSpoutInput = new KafkaSpout<>(kafkaSpoutConfig);
			//KafkaSpout<String, Read> kafkaSpoutInput = new KafkaSpout<>(kafkaSpoutConfig);
	
			//Risk of failure
			ConsumerTopic.PairConsumerTopic(ConsumerGrp,Topic);
			
			tp.setSpout("kafka_spout",kafkaSpoutInput, 1);
			tp.setBolt("sentence-splitter", new splitter(), 1).shuffleGrouping("kafka_spout");
			System.out.println("Spout set");

			//NIMBUS ERRORS HERE
			LocalCluster localCluster = new LocalCluster();
			localCluster.submitTopology("Stocktopology",new Config(),tp.createTopology());
			Thread.sleep(30000);
		}
		else{
			System.out.println("Topic doesnt exist!!");
		}
    }

}
