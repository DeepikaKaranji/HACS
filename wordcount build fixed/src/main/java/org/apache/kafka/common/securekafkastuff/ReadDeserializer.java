package org.apache.kafka.common.securekafkastuff;
import org.apache.kafka.common.securekafkastuff.Read;
import org.apache.kafka.common.serialization.Deserializer;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectInput;
import java.util.Map;
import java.io.IOException;

public class ReadDeserializer implements Deserializer<Read> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {    
    }

    @Override
    public Read deserialize(String topic, byte[] data) {
		try 
		{
		    //return objectMapper.readValue(new String(data, "UTF-8"), TestDto.class);
		        if(data==null)
		        {
					return null;
				}
				else
				{
					ByteArrayInputStream bis = new ByteArrayInputStream(data);
					System.out.println("BIS: "+bis);
					ObjectInput in = new ObjectInputStream(bis);
					return (Read)in.readObject();				
				}
		//return null;
		} 
	  	catch (Exception e) 
	  	{
		    //log.error("Unable to deserialize message {}", data, e);
		    System.out.println("Data is: "+data);
		    System.out.println("Error: "+e);
		    return null;
		}
    }

    @Override
    public void close() {
    }
}
