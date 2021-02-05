package org.apache.kafka.common.securekafkastuff;
import java.lang.String;

public class encapsulator{
	private String acc;
	private String data;
	private String schema;
	
	public String getAcc(){
		return acc;
	}
	
	public String getSchema(){
		return schema;
	}
	public String getData(){
		return data;
	}
	
	public encapsulator(String AccControl,String Data,String Schema){
		schema = Schema;
		acc = AccControl;
		data = Data;
	}
	
	public encapsulator(){
	
	}
}
