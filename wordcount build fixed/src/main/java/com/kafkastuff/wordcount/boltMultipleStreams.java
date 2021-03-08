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
import java.util.List;
import java.util.ArrayList;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.io.BufferedWriter;

import java.io.PrintStream;
import java.io.FileNotFoundException;
//import org.apache.kafka.common.securekafkastuff.Read;
import org.apache.kafka.common.securekafkastuff.encapsulator;
import org.apache.kafka.common.securekafkastuff.imposer;
import org.apache.kafka.common.securekafkastuff.readImposer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;
import org.apache.kafka.common.securekafkastuff.joinImposer;

public class boltMultipleStreams extends BaseWindowedBolt {

	OutputCollector collector;
	public Integer Runs;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	/*
	public splitter(){
		Runs = 0;
	}
	*/

	@Override
	public void execute(TupleWindow inputWindow){
	
		System.out.println("In execute function of spliter\n");
		List<Tuple> input = new ArrayList<Tuple>();
		input = inputWindow.get();
		encapsulator t1 = encapsulator.class.cast(input.get(0).getValue(4));
		//imposer r1 = new readImposer(t1);
		
		
		//input = inputWindow.get();
		encapsulator t2 = encapsulator.class.cast(input.get(1).getValue(4));
		//imposer r2 = new readImposer(t2);
		
		/*
		for(Tuple input: inputWindow.get()) {
			encapsulator sentence = encapsulator.class.cast(input.getValue(4));
			//System.out.println("Data: "+sentence.getData());
			//System.out.println("AccControl: "+sentence.getAcc());
			//System.out.println("Schema: "+sentence.getSchema());
     		}
     		*/
     		
     		joinImposer joinObj = new joinImposer(t1, t2);
     		imposer output = joinObj.join();
     		System.out.println("OUTPUT Data: "+output.read());

		
		/*
		imposer r = new readImposer(sentence);
		
		String data = r.read();
		if(data!=null){
			try {
				BufferedWriter fObj2 = new BufferedWriter(new FileWriter("raw.txt",true));
				String[] KV = data.split(",",2);
				fObj2.write("BallNumber - " + KV[0] + ", Score - " + KV[1] + "\n");
				fObj2.close();
				Runs += Integer.parseInt(KV[1]);
				if (Integer.parseInt((KV[0])) % 6 == 0){
					Double RunRate = Double.valueOf(Runs) / 6;
		Integer Overs = Integer.parseInt(KV[0]) / 6;
					BufferedWriter fObj3 = new BufferedWriter(new FileWriter("processed.txt",true));
					fObj3.write("OverNumber - " + Overs +", RunsScored - "+Runs+", RunRate - "+ RunRate+"\n");
					fObj3.close();
					Runs = 0;
				}
				//System.out.println(data);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			collector.emit(new Values(data));
			
		}
		*/
		
		collector.emit(new Values("Completed!"));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("wordMap"));
	}
}

