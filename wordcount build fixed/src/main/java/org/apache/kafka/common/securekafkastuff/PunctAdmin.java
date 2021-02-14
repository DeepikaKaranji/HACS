package org.apache.kafka.common.securekafkastuff;

import org.apache.kafka.common.securekafkastuff.Topics;
import java.util.Properties;
import java.util.HashMap;
import org.apache.kafka.common.securekafkastuff.SecureMaps;

public class PunctAdmin{
	private Topics t = new Topics();
	//Boolean DeleteTopic = false;<TopicName,Boolean>
	
	public HashMap<String,Boolean> DeleteTopic =  new HashMap<String,Boolean>();
	
	
	public Boolean createTopic(String topicName, Properties props){
		Boolean output = t.createTopic(topicName,1,1,props);
		if(output==true){
			System.out.println("Topic Created!");
			DeleteTopic.put(topicName,true);
			//DeleteTopic.put("NICE",true);
			System.out.println(DeleteTopic.size());
			
			SecureMaps SecMapObj = new SecureMaps();
			SecMapObj.AddTopic(topicName);
		}
		return output;
	}
	
	public Boolean deleteTopic(String topicName, Properties props){
		Boolean output = false;
		if(DeleteTopic.get(topicName)==true){
			output = t.deleteTopic(topicName,props);
			DeleteTopic.remove(topicName);
			System.out.println("Delete Worked: "+DeleteTopic.size());
		}
		return output;
	}
}
// DeleteTopic is a flag variable that will state hey go ahead and delete. Admin=>createTopic()=>while(flag != true)=>deleteTopic()


