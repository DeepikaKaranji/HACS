package org.apache.kafka.common.securekafkastuff;

public class Write{

	private String data;
	
	public void insertData(String Data){
		data = Data;
	}
	
	public void changeData(String Data){
		data = Data;
	}
	
	public String getData(){
		return data;
	}
}
