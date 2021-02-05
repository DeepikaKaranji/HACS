package com.kafkastuff.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.io.BufferedWriter;

import java.io.PrintStream;
import java.io.FileNotFoundException;
//import org.apache.kafka.common.securekafkastuff.Read;
import org.apache.kafka.common.securekafkastuff.encapsulator;
import org.apache.kafka.common.securekafkastuff.imposer;
import org.apache.kafka.common.securekafkastuff.readImposer;

public class splitter extends BaseRichBolt {

	OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input){
		System.out.println("In execute function of spliter\n");
		encapsulator sentence = encapsulator.class.cast(input.getValue(4));
		
		imposer r = new readImposer(sentence);
		
		String data = r.read();
		if(data!=null){
			try {
				BufferedWriter fObj = new BufferedWriter(new FileWriter("output.txt",true));
				fObj.write(data+"\n");
				fObj.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			collector.emit(new Values(data));
		}
		
		
		//String sentence = input.getValue(4).toString();
		//System.out.println("Sentence : " + sentence);
		/*
		if (sentence != null) {
			//System.out.println(sentence.getData());
			try {
				BufferedWriter fObj = new BufferedWriter(new FileWriter("output.txt",true));
				fObj.write(sentence.getData()+"\n");
				fObj.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			collector.emit(new Values(sentence.getData()));
		}
		*/
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("wordMap"));
	}
}

