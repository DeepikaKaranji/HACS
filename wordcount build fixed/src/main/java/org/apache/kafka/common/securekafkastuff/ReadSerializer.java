package org.apache.kafka.common.securekafkastuff;
import org.apache.kafka.common.securekafkastuff.Read;
import org.apache.kafka.common.serialization.Serializer;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.io.IOException;

public class ReadSerializer implements Serializer<Read>
{

    @Override
    public void configure(Map<String, ?> configs, boolean isKey)
    {
    	
    }

    @Override
    public byte[] serialize(String topic, Read read)
    {
        try 
        {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			if(read==null){
				System.out.println("m here!");
				oos.writeObject(null);
			}
			else{
				oos.writeObject(read);
			}
			oos.close();
			byte[] b = baos.toByteArray();
			return b;
        } 
        catch (IOException e) 
        {
        	System.out.println("There is an IO exception");
			return new byte[0];
        }
    }

    @Override
    public void close()
    {

    }
}
