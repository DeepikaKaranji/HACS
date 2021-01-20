package org.apache.kafka.common.securekafkastuff;

import org.apache.kafka.common.securekafkastuff.Read;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser.Feature;


public class ReadDeserializeroo implements Deserializer<Read> {
	@Override 
	public void close() {}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	
	}

	@Override
	public Read deserialize(String topic, byte[] data) {//String topic, byte[] data
		System.out.println("I am ReadDeserializeroo\n");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		Read read = null;
		System.out.println("Size: "+data.length);
		try {
			read = mapper.readValue(data, Read.class);
		} catch (Exception e) {
			System.out.println("READ: "+read);
			System.out.println("Exception: "+e);
			
			//e.printStackTrace();
		}
		return read;
	}
}
