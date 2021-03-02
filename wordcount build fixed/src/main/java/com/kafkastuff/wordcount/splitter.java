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

import java.io.FileWriter;   
import java.io.IOException;
import java.io.BufferedWriter;

import java.io.PrintStream;
import java.io.FileNotFoundException;
//import org.apache.kafka.common.securekafkastuff.Read;
import org.apache.kafka.common.securekafkastuff.encapsulator;
import org.apache.kafka.common.securekafkastuff.imposer;
import org.apache.kafka.common.securekafkastuff.readImposer;
import java.lang.instrument.Instrumentation;

public class splitter extends BaseRichBolt {

	OutputCollector collector;
	private static Instrumentation instrumentation;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input){
		System.out.println("In execute function of spliter\n");
		encapsulator sentence = encapsulator.class.cast(input.getValue(4));
		try{
			BufferedWriter fObj = new BufferedWriter(new FileWriter("demo_encap.txt",true));
			fObj.write("========================"+"\n");
			fObj.write(sentence.getAcc()+"\n");
			fObj.write(sentence.getData()+"\n");
			fObj.write(sentence.getSchema()+"\n");		
			fObj.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
		

				
		imposer r = new readImposer(sentence);
		if(r.read()!=null){
			try {
				BufferedWriter fObj = new BufferedWriter(new FileWriter("output.txt",true));
				fObj.write(r.read()+"\n");
				fObj.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			collector.emit(new Values(r.read()));
		}
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("wordMap"));
	}
}

