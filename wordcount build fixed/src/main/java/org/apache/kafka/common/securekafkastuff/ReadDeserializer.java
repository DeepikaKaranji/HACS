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
        try {
            //return objectMapper.readValue(new String(data, "UTF-8"), TestDto.class);
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
    		ObjectInput in = new ObjectInputStream(bis);
    		return (Read)in.readObject();
  		} 
  		catch (Exception e) {
            //log.error("Unable to deserialize message {}", data, e);
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}
