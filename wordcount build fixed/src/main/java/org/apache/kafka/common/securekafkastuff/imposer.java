package org.apache.kafka.common.securekafkastuff;
import org.apache.kafka.common.securekafkastuff.encapsulator;


public class imposer{
	protected String accControl;
	protected String data;
	protected String schema;
	
	public imposer(encapsulator e){
		//System.out.println("Info is: "+e.getterAcc()+e.getterData()+e.getterSchema());
		this.accControl = e.getAcc();
		this.data = e.getData();
		this.schema = e.getSchema();
	}
	
	/*
	protected String setData(encapsulator e){
		return e.getterData();
	}
	
	protected String setAcc(encapsulator e){
		return e.getterAcc();
	}
	protected String setSchema(encapsulator e){
		return e.getterSchema();
	}
	*/
	
	
	
	public String read(){
		return "Please extend and use!";
	}
}
