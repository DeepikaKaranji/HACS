package org.apache.kafka.common.securekafkastuff;
import org.apache.kafka.common.securekafkastuff.imposer;
import org.apache.kafka.common.securekafkastuff.encapsulator;
import org.apache.kafka.common.securekafkastuff.readImposer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.lang.String;
import com.google.common.collect.Sets;
import java.util.concurrent.ConcurrentHashMap;


public class joinImposer{	
	private Map<String,String> map_r1 = new ConcurrentHashMap<String,String>();
	private Map<String, String> map_r2 = new ConcurrentHashMap<String,String>();
	private Map<String, String> map_join = new ConcurrentHashMap<String,String>();
	
	private Set<String> A1 = new HashSet<String>();
	private Set<String> A2 = new HashSet<String>();
	
	private Set<String> S1 = new HashSet<String>();
	private Set<String> S2 = new HashSet<String>();
	
	
	
	public joinImposer(encapsulator r1, encapsulator r2){
		String acc_type1 = r1.getAcc().split(":",0)[0];
		String acc1[] = r1.getAcc().split(":",0)[1].split(",");
		
		String acc_type2 = r2.getAcc().split(":",0)[0];
		String acc2[] = r2.getAcc().split(":",0)[1].split(",");
		
		int length1 = acc1.length;
		int length2 = acc2.length;
		
		for(int i = 0;i<length1;i++){
			A1.add(acc1[i]);
		}
		
		for(int i = 0;i<length2;i++){
			A2.add(acc2[i]);
		}
		
		if(acc_type1.equals("READ") && acc_type2.equals("READ")){
			String arr1[] = r1.getSchema().split(",");
			String arr2[] = r2.getSchema().split(",");
			
			String data1[] = r1.getData().split(",",0);
			String data2[] = r2.getData().split(",",0);
			
			for (int i = 0;i<arr1.length;i++){
				S1.add(arr1[i]);
				map_r1.put(arr1[i],data1[i]);
			}
			
			for (int i = 0;i<arr2.length;i++){
				S2.add(arr2[i]);
				map_r2.put(arr2[i],data2[i]);
			}
		}
	}
	
	public imposer join(){
		Set<String> topic1_table_attr = Sets.intersection(this.S1,this.A1);
		Set<String> topic2_table_attr = Sets.intersection(this.S2,this.A2);
		for(String s:this.map_r1.keySet()){
			if(!topic1_table_attr.contains(s)){
				this.map_r1.remove(s);
			}
		}
		
		for(String s:this.map_r2.keySet()){
			if(!topic2_table_attr.contains(s)){
				this.map_r2.remove(s);
			}
		}

		Set<String> x = Sets.difference(this.A1,this.A2);
		Set<String> y = Sets.difference(this.A2,this.A1);
		Set<String> z = Sets.intersection(this.S1,this.S2);
		
		for(String i:x){
			if(z.contains(i)){
				this.map_r1.remove(i);
				this.map_r2.remove(i);
			}
		}
		
		for(String i:y){
			if(z.contains(i)){
				this.map_r1.remove(i);
				this.map_r2.remove(i);
			}
		}
		
		Boolean joinable = true;
		Set<String> joinableSet = Sets.intersection(this.map_r1.keySet(),this.map_r2.keySet());
		for (String i: joinableSet){
			if(this.map_r1.get(i)!=this.map_r2.get(i)){
				joinable = false;
			}
		}
		
		if(joinable){
			this.map_r1.putAll(this.map_r2);
		}
		
		//System.out.println("Output: "+this.map_r1);
		//encapsulator e1 = new encapsulator("READ:a,b","1,2,3,5","a,b,c,e");
		String outputAccControl = "READ:";
		String outputData = "";
		String outputSchema = "";
		for(Map.Entry<String,String> entry : map_r1.entrySet()){
			outputAccControl = outputAccControl+entry.getKey()+",";
			outputData = outputData+entry.getValue()+",";
			outputSchema = outputSchema+entry.getKey()+",";
		}
		//encapsulator output = new 
		encapsulator output  = new encapsulator(outputAccControl.substring(0, outputAccControl.length() - 1),
							outputData.substring(0, outputData.length() - 1),
							outputSchema.substring(0, outputSchema.length() - 1));
		imposer outputImposer = new readImposer(output);
		return outputImposer;
	}
	
}
