package org.apache.kafka.common.securekafkastuff;

public class Read{
	private final String data;
	
	public Read(String Data){
		data = Data;
	}
	
	
	public String getData(){
		return data;
	}
}
