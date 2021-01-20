package org.apache.kafka.common.securekafkastuff;

import org.apache.kafka.common.securekafkastuff.Read;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadSerializeroo implements Serializer<Read> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey)
	{

	}


	@Override 
	public byte[] serialize(String arg0, Read arg1) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(arg1).getBytes();
			
		} catch (Exception e) {
			System.out.println(e);
			//e.printStackTrace();
		}
		System.out.println("Length is: "+retVal.length);
		return retVal;
	}
	
	@Override 
	public void close() {

	}
}
