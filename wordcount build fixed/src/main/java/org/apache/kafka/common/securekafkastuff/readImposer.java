package org.apache.kafka.common.securekafkastuff;
import org.apache.kafka.common.securekafkastuff.imposer;
import org.apache.kafka.common.securekafkastuff.encapsulator;


public class readImposer extends imposer{
	private final String dataRead;
	//private String accControl;
	//private String schema;
	
	public readImposer(encapsulator e){
		//accControl = setAcc(e);
		super(e);
		System.out.println("ACC is: "+super.accControl);
		System.out.println("Equals: "+super.accControl.equals("READ"));
		if(super.accControl.equals("READ")){
			System.out.println("Entered IF");
			this.dataRead = super.data;
		}
		else{
			System.out.println("Entered ELSE");
			this.dataRead = null;
		}
		//this.dataRead = super.data;
		
		//schema = setSchema(e);
		
	}
	
	public String read(){
		String output = "No access";
		if(this.dataRead!=null){
			output = dataRead;
		}
		return output;
	}	
	
}
