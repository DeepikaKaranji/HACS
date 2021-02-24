package org.apache.kafka.common.securekafkastuff;

import org.json.*;
import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import org.apache.commons.io.FileUtils;



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


public class SecureMapsAdmin{
	
	//We read from the json file and then store it into maps.

	public static JSONObject Maps;

	public SecureMapsAdmin(){
		Maps = new JSONObject();
		try{
			File file = new File("Rules.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			Maps = new JSONObject(content);
			//System.out.println(Maps);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	// Function to Insert Topic into Maps
	
	// Returns 0 on success, 1 otherwise
	
	/*
	public Boolean ReadJSONFile(){
		try{
			File file = new File("employees.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			Maps = new JSONObject(content);
			System.out.println(Maps);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return true;	
	}
	*/
	
	protected Boolean WriteJSONFile(){
		try{
			FileWriter file = new FileWriter("Rules.json");
			file.write(Maps.toString());
			file.flush();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}
	
	protected int AddTopic(String Topic){
		if (!Maps.getJSONObject("rules").has(Topic)){
			Maps.getJSONObject("rules").put(Topic, new JSONObject());
			WriteJSONFile();
			return 0;
		}
		return 1;
	}


	// //Funtion to Insert Consumer Group
	// //Returns 0 on success, 1 otherwise
	protected int AddConsumerGroup(String Topic, String ConsumerGroup){
		if (Maps.getJSONObject("rules").has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject("rules").getJSONObject(Topic);
			if (!TopicObj.has(ConsumerGroup)){
				TopicObj.put(ConsumerGroup, new JSONArray());
				WriteJSONFile();
				return 0;
			}
			return 1;
		}
		return 1;
	}
	
	
	
	protected int AddRuleAdmin(String Topic, String ConsumerGroup, String Permission){
		if(Maps.getJSONObject("rules").has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject("rules").getJSONObject(Topic);
			System.out.println("Map: "+TopicObj);
			if (TopicObj.has(ConsumerGroup)){
				if(Maps.getJSONObject("rules").getJSONObject(Topic).getJSONArray(ConsumerGroup).length()==0){
					System.out.println("In if loop");
					JSONObject ConsumerDetails = new JSONObject();
					ConsumerDetails.put("Permission", Permission);
					JSONObject temp1 = Maps.getJSONObject("rules").getJSONObject(Topic);	
					JSONArray temp2 = temp1.getJSONArray(ConsumerGroup);
					temp2.put(ConsumerDetails);	
				}
				else{
					System.out.println("In else loop");
					JSONArray Permissions = Maps.getJSONObject("rules").getJSONObject(Topic).getJSONArray(ConsumerGroup);
					Permissions.getJSONObject(0).put("Permission",Permission);
				}
				System.out.println("Maps: "+Maps);
				WriteJSONFile();
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
		return Maps.getJSONObject("rules").keySet();
	}

	public boolean CheckTopic(String Topic){
		Set<String> Topics = GetTopics();
		for(String topics : Topics){
			if (topics.equals(Topic)) return false;
		}
		return true;
	}


	// //Function to Get Consumer Groups from a topic
	// //Returns a Set of consumer groups
	public Set<String> GetConsumerGroups(String Topic){
		return Maps.getJSONObject("rules").getJSONObject(Topic).keySet();
	}

	public boolean CheckConsumerGroups(String Topic, String ConsumerGroup){
		Set<String> ConsumerGroups = GetConsumerGroups(Topic);
		for(String consumers : ConsumerGroups){
			if (ConsumerGroup.equals(consumers)) return false;
		}
		return true;
	}

	//Function to Get Consumers from a Topic and Consumer Group
	//Returns a Vector of Consumers if valid topic and consumer group are provided, otherwise empty vector
	/*
	public JSONArray GetConsumers(String Topic, String ConsumerGroup){
		if (Maps.getJSONObject("rules").has(Topic) && Maps.getJSONObject("rules").getJSONObject(Topic).has(ConsumerGroup)){
			return Maps.getJSONObject("rules").getJSONObject(Topic).getJSONArray(ConsumerGroup);
		}
		return new JSONArray();
	}
	*/

	// // Funtion to Update Consumer Permissions
	// // Returns 0 on success, 1 otherwise
	/*
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
	*/
	
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
		JSONArray Permissions = Maps.getJSONObject("rules").getJSONObject(Topic).getJSONArray(ConsumerGroup);
		String permission = Permissions.getJSONObject(0).getString("Permission");
		if(permission.equals(Permission)){
			return true;
		}
		return false;

	}
	

	public JSONObject GetMaps(){
		return Maps.getJSONObject("rules");
	}
}
