package org.apache.kafka.common.securekafkastuff;

import org.json.*;
import java.io.*; 
import java.util.*; 


// topic1:{
// 	cg1:{
// 		[
// 			{"c1": a12, "permission": 110, "hostIP": "*"},
// 			{"c1": a13, "permission": 110, "hostIP": "*"},
// 			{"c1": a23, "permission": 111, "hostIP": "*"},
// 		]
// 	}
// }


//{
//    topic1:{
//       cg1: {
//			[
//			{"ConsumerId" : 34, "Permission": xx, "hostIP": xx},
// 			{"ConsumerId" : xx, "Permission": xx, "hostIP": xx},		
// 		 ]},
//       cg2: [
//			{"Consumer1" : xx, "Permission": xx, "hostIP": xx),
// 			{"Consumer2" : xx, "Permission": xx, "hostIP": xx),		
// 		 ]},
//    },
//    topic2:{
//       cg1: [
//			{"Consumer1" : xx, "Permission": xx, "hostIP": xx),
// 			{"Consumer2" : xx, "Permission": xx, "hostIP": xx),		
// 		 ]},
//       cg2: [
//			{"Consumer1" : xx, "Permission": xx, "hostIP": xx),
// 			{"Consumer2" : xx, "Permission": xx, "hostIP": xx),		
// 		 ]},
//    }
//}


public class SecureMaps {
	// Private Members
	JSONObject Maps;

	//Public Members

	//Constructor to initialize Maps
	public SecureMaps(){
		Maps = new JSONObject();
	}

	// Function to Insert Topic into Maps
	
	// Returns 0 on success, 1 otherwise
	public int AddTopic(String Topic){
		if (!Maps.has(Topic)){
			Maps.put(Topic, new JSONObject());
			return 0;
		}
		return 1;
	}

	// //Funtion to Insert Consumer Group
	// //Returns 0 on success, 1 otherwise
	public int AddConsumerGroup(String Topic, String ConsumerGroup){
		if (Maps.has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject(Topic);
			if (!TopicObj.has(ConsumerGroup)){
				TopicObj.put(ConsumerGroup, new JSONArray());
				return 0;
			}
			return 1;
		}
		return 1;
	}

	// //Funtion to Insert Consumer Details
	// //Returns 0 on success, 1 otherwise
	public int AddConsumer(String Topic, String ConsumerGroup, String ConsumerID, String Permissions, String HostIP){
		if (Maps.has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject(Topic);
			if (TopicObj.has(ConsumerGroup)){
				JSONObject ConsumerDetails = new JSONObject();
				ConsumerDetails.put("ConsumerID", ConsumerID);
				ConsumerDetails.put("Permissions", Permissions);
				ConsumerDetails.put("HostIP", HostIP);
				JSONObject temp1 = Maps.getJSONObject(Topic);
				JSONArray temp2 = temp1.getJSONArray(ConsumerGroup);
				temp2.put(ConsumerDetails);
				return 0;
			}
			return 1;
		}
		return 1;
	}

	// //Function to Get Consumer Groups from a topic
	// //Returns a Set of consumer groups
	public Set<String> GetConsumerGroups(String Topic){
		return Maps.getJSONObject(Topic).keySet();
	}

	//Function to Get Consumers from a Topic and Consumer Group
	//Returns a Vector of Consumers if valid topic and consumer group are provided, otherwise empty vector
	public JSONArray GetConsumers(String Topic, String ConsumerGroup){
		if (Maps.has(Topic) && Maps.getJSONObject(Topic).has(ConsumerGroup)){
			return Maps.getJSONObject(Topic).getJSONArray(ConsumerGroup);
		}
		return new JSONArray();
	}

	// // Funtion to Update Consumer Permissions
	// // Returns 0 on success, 1 otherwise
	public int UpdateConsumerPermission(String Topic, String ConsumerGroup, String Consumer, String Permissions){
		if (Maps.has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject(Topic);
			//System.out.println(TopicObj);
			if (TopicObj.has(ConsumerGroup)){
				JSONArray ConsumerGroupArr = TopicObj.getJSONArray(ConsumerGroup);
				for(int i = 0 ; i < ConsumerGroupArr.length() ; i++){
					if(ConsumerGroupArr.getJSONObject(i).getString("ConsumerID") == Consumer){
						ConsumerGroupArr.getJSONObject(i).put("Permissions", Permissions);
						return 0;
					}
				}
			}
			return 1;
		}
		return 1;
	}

	// Funtion to Update Consumer HostIp
	// Returns 0 on success, 1 otherwise
	public int UpdateConsumerHostIP(String Topic, String ConsumerGroup, String Consumer, String HostIP){
		if (Maps.has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject(Topic);
			if (TopicObj.has(ConsumerGroup)){
				JSONArray ConsumerGroupArr = TopicObj.getJSONArray(ConsumerGroup);
				for(int i = 0 ; i < ConsumerGroupArr.length() ; i++){
					if(ConsumerGroupArr.getJSONObject(i).getString("ConsumerID") == Consumer){
						ConsumerGroupArr.getJSONObject(i).put("HostIP", HostIP);
						return 0;
					}
				}
			}
			return 1;
		}
		return 1;
	}
	
	//TODO: Write a function to get consumer deets
	
	public int CheckPermission(String Topic, String ConsumerGroup, String ConsumerID, String Permission){
		JSONArray Consumers = GetConsumers(Topic, ConsumerGroup);
			for (int j = 0 ; j < Consumers.length(); ++j){
				JSONObject consumer = Consumers.getJSONObject(j);
				if (consumer.getString("ConsumerID") == ConsumerID && consumer.getString("Permissions") == Permission) return 0;
			}
			return 1;
	}

	public JSONObject GetMaps(){
		return Maps;
	}
}
