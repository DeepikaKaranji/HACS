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
        if (read == null) 
        {
            return null;
        }
        try 
        {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(read);
			oos.close();
			byte[] b = baos.toByteArray();
			return b;
        } 
        catch (IOException e) 
        {
			return new byte[0];
        }
    }

    @Override
    public void close()
    {

    }
}
