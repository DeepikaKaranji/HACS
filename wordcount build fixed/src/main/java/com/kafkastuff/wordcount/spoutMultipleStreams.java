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
import org.apache.kafka.common.securekafkastuff.SecureMapsAdmin;
import java.util.Set;
import java.util.HashSet;
import com.kafkastuff.wordcount.boltMultipleStreams;
import org.apache.storm.topology.base.BaseWindowedBolt.Count;


public class spoutMultipleStreams
{
    public static void main( String[] args ) throws AlreadyAliveException, InterruptedException, org.apache.storm.thrift.TException, java.lang.Exception
    {

		String zkConnString = "localhost:2181";
		System.out.println("Config given for Spout");
		final TopologyBuilder tp = new TopologyBuilder();
		System.out.println("Empty Topology created");

		String ConsumerGrp = "cgrp";
		
		String Topic1 = "t1";
		String Topic2 = "t2";
		Set<String> topics = new HashSet<String>();
		topics.add(Topic1);
		topics.add(Topic2);
		

		//SecureMapsAdmin admin = new SecureMapsAdmin();
		
		//if(admin.CheckTopic(Topic) == false && admin.CheckConsumerGroups(Topic, ConsumerGrp) == false){
		
		KafkaSpoutConfig<String, String> kafkaSpoutConfig = KafkaSpoutConfig.builder("localhost:9092",topics)
		.setProp(ConsumerConfig.GROUP_ID_CONFIG, ConsumerGrp)
		.setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
		.setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.securekafkastuff.encapDeserializer")
		.build();
		KafkaSpout<String,String> kafkaSpoutInput = new KafkaSpout<>(kafkaSpoutConfig);

		//ConsumerTopic.PairConsumerTopic(ConsumerGrp,Topic);
		
		tp.setSpout("kafka_spout",kafkaSpoutInput, 1);
		tp.setBolt("sentence-splitter", new boltMultipleStreams().withWindow(new Count(2), new Count(2)), 1).shuffleGrouping("kafka_spout");
		System.out.println("Spout set");

		//NIMBUS ERRORS HERE
		LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("CricketTopology",new Config(),tp.createTopology());
		Thread.sleep(30000);
		
		//}
		//else{
		//	System.out.println("Topic doesnt exist!!");
		//}
    }

}
