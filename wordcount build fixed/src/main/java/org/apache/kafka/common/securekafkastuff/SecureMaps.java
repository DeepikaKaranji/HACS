import org.json.*;
import java.io.*; 
import java.util.*; 


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


class SecureMaps {
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
		return Maps.keySet();
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
	public int UpdateConsumerPermission(String Topic, String Consumer, String ConsumerGroup, String Permissions){
		if (Maps.has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject(Topic);
			if (TopicObj.has(ConsumerGroup)){
				JSONArray ConsumerGroupArr = Maps.getJSONArray(ConsumerGroup);
				for(int i = 0 ; i < ConsumerGroupArr.length() ; i++){
					if(ConsumerGroupArr.getJSONObject(i).getString("ConsumerID") == Consumer){
						ConsumerGroupArr.getJSONObject(i).put("Permission", Permissions);
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
	public int UpdateConsumerHostIP(String Topic, String Consumer, String ConsumerGroup, String HostIP){
		if (Maps.has(Topic)){
			JSONObject TopicObj = Maps.getJSONObject(Topic);
			if (TopicObj.has(ConsumerGroup)){
				JSONArray ConsumerGroupArr = Maps.getJSONArray(ConsumerGroup);
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

	
	public static void main(String argv[]){
		SecureMaps Obj1 = new SecureMaps();
	}
}