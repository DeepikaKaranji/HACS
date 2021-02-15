package org.apache.kafka.common.securekafkastuff;

import org.json.*;
import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.json.simple.JSONObject;
//import org.json.simple.JSONArray;



// topic1:{
// 	cg1:{
//		[
//			{"permission":""}
//		]
// 	}
// }




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


public class SecureMaps{
	
	//We read from the json file and then store it into maps.

	public static JSONObject Maps;

	public SecureMaps(){
		Maps = new JSONObject();
	}

	// Function to Insert Topic into Maps
	
	// Returns 0 on success, 1 otherwise
	
	public Boolean ReadJSONFile(){
		JSONParser jsonParser = new JSONParser();
		try{
			FileReader reader = new FileReader("employees.json");
			Object obj = jsonParser.parse(reader);
			JSONObject employeeList = (JSONObject) obj;
			System.out.println(employeeList);
		}
		
		catch (FileNotFoundException e) {
            		e.printStackTrace();
        	} catch (IOException e) {
            		e.printStackTrace();
        	} catch (ParseException e) {
            		e.printStackTrace();
        	}
		return true;
	}
	
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
	
	
	
	public int AddRuleAdmin(String Topic, String ConsumerGroup, String Permission){
		if(Maps.has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject(Topic);
			if (TopicObj.has(ConsumerGroup)){
				//check if json is empty. add like rn. else persmissions and update
				if(Maps.getJSONObject(Topic).getJSONArray(ConsumerGroup).length()==0){
					System.out.println("In if loop");
					JSONObject ConsumerDetails = new JSONObject();
					ConsumerDetails.put("Permission", Permission);
					JSONObject temp1 = Maps.getJSONObject(Topic);	
					JSONArray temp2 = temp1.getJSONArray(ConsumerGroup);
					temp2.put(ConsumerDetails);	
				}
				else{
					System.out.println("In else loop");
					JSONArray Permissions = Maps.getJSONObject(Topic).getJSONArray(ConsumerGroup);
					Permissions.getJSONObject(0).put("Permission","");
				}
				return 0;				
			}
			return 1;
		}
		return 1;
	}
	
	
	/*
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
	*/
	//Function to return topics
	//Returns set of topics
	
	public Set<String> GetTopics(){
		return Maps.keySet();
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
	/*
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
	*/
	
	//TODO: Write a function to get consumer deets
	/*
	public int CheckPermission(String Topic, String ConsumerGroup, String ConsumerID, String Permission){
		JSONArray Consumers = GetConsumers(Topic, ConsumerGroup);
			for (int j = 0 ; j < Consumers.length(); ++j){
				JSONObject consumer = Consumers.getJSONObject(j);
				if (consumer.getString("ConsumerID") == ConsumerID && consumer.getString("Permissions") == Permission) return 0;
			}
			return 1;
	}
	*/
	public Boolean CheckPermissionAdmin(String Topic, String ConsumerGroup, String Permission){
		JSONArray Permissions = Maps.getJSONObject(Topic).getJSONArray(ConsumerGroup);
		String permission = Permissions.getJSONObject(0).getString("Permission");
		if(permission.equals(Permission)){
			return true;
		}
		return false;

	}
	

	public JSONObject GetMaps(){
		return Maps;
	}
}
