package com.kafkastuff.wordcount;
import java.util.Properties;
import java.util.Collections;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.kafka.clients.producer.Producer;
//import com.kafkastuff.wordcount.KafkaProducer;
//import com.kafkastuff.wordcount.Producer_kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.securekafkastuff.Topics;

    //makeCgKey(cg_name, permissions, hostIP) -> returns cgKey

    //SetConsumerProperties(consumerID, permissions, HostIP) -> returns consumer
                        //consumer = Collection of [consumerID, consumer Permissions, HostIP access]

    //InsertTopicMap(TopicID, cgKey)
    //DeleteTopicMap
    //UpdateTopicMap

    //InsertCgMap(cgKey, consumer)
    //DeleteCGMap
    //UpdateCGMap

    //InsertRule(TopicID, cgKey, consumer)
        //calls InsertTopicMap
        //calls InsertCGMap
        //When both finished successfully, rule inserted.

    //DeleteRule(TopicID, cgKey, consumer)
        //calls DeleteTopicMap
        //calls DeleteCGMap
        //When both finished successfully, rule deleted.
    
    //UpdateRule(TopicID, cgKey, consumer)
        //calls UpdateTopicMap
        //calls UpdateCGMap
        //When both finished successfully, rule updated.


    class SecureMaps {
        //cgKeysList [[cgKey]] -> list of cgKeys. Each cgKey is a list by itself.
        public static ArrayList<ArrayList> cgKeysList = new ArrayList<ArrayList>();
        //topicMap {TopicID: cgKeysList} map
        // private static HashMap<String, ArrayList> topicMap = new HashMap<String, ArrayList>();
        public static Map<String, ArrayList> topicMap;
        static {
            topicMap = new HashMap<>();
            topicMap.put("topic1", );
            topicMap.put("topic2", );
        }
        //cgMap {cgKey:consumerList}
        private static HashMap<ArrayList, ArrayList> cgMap = new HashMap<ArrayList, ArrayList>();

        public String insertToTopicMap(String topicID, ArrayList cgKeysList){
            return topicMap.put(topicID, cgKeysList);
        }
        public String insertToCgMap(ArrayList cgKey, ArrayList consumer){
            return cgMap.put(cgKey,consumer);
        }
        public String deleteFromTopicMap(String TopicID){
            return topicMap.remove(TopicID);
        }
        public String deleteFromCgMap(ArrayList cgKey){
            return cgMap.remove(cgKey);
        }
        // public String updateTopicMap(ArrayList cgKey, ArrayList consumer){
        //     return cgMap.put(cgKey,consumer);
        // }
        //public String updateCgMap()
    }
    // static obj of secure maps

    class ConsumerGroup{
        String cgName;
        String permissions;
        String hostIP;
        
        public ConsumerGroup(String v1, String v2, String v3){
            this.cgName = v1;
            this.permissions = v2;
            this.hostIP = v3;
        }
        public Arraylist makeCgKey(String v1, String v2, String v3, ){
                return ArrayList<String> cgKey = new ArrayList<String>(v1,v2,v3);
        }

    }
    public Arraylist setConsumerProperties(String consumerID, String permissions, String hostIP){
        return ArrayList<String> consumer = new ArrayList<String>(consumerID, permissions, hostIP);
    }

    public Arraylist insertRule(String topicID, ArrayList cgKey, ArrayList consumer, SecureMaps sm){
        sm.insertTopicMap();
        sm.insertCgMap();
    }


//##################################################################
//where to create cgkeylist
//Static "field" of Sm?? distributed ? -> References, deep copy, RACE??
                                        //References, Static
//Sync when you delete and update a rule?
//Why are we not using a DB?? AdminClient is doing stuff - look it up 
//None of the packages i'm importing are working.
    //include in pom file
    //google library name maven
//#################################################################
