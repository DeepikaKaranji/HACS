package org.apache.kafka.common.securekafkastuff;

import org.apache.kafka.common.securekafkastuff.encapsulator;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser.Feature;


public class encapDeserializer implements Deserializer<encapsulator> {
	@Override 
	public void close() {}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	
	}

	@Override
	public encapsulator deserialize(String topic, byte[] data) {//String topic, byte[] data
		System.out.println("I am encapDeserializer\n");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		encapsulator read = null;
		System.out.println("Size: "+data.length);
		try {
			read = mapper.readValue(data, encapsulator.class);
		} catch (Exception e) {
			System.out.println("READ: "+read);
			System.out.println("Exception: "+e);
			
			//e.printStackTrace();
		}
		return read;
	}
}
