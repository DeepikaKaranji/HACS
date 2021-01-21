package org.apache.kafka.common.securekafkastuff;

public class Read{
	//private final String data;
	private String data;
	
	public String getData(){
		return data;
	}
	
	public Read(String Data){
		data = Data;
	}	
	
	public Read(){
	
	} //Comment dummy constructor if using a final variable	
	
	/* A) When data is a final variable
		  (i) Order of the getData() function and the constructor matters. If getData() is defined before the constructor then the size of the byte array
		  'data' (printed in the App.java terminal) in the ReadDeserializeroo is a positive value (typically 30 after multiple rounds of execution), which is a good
		  sign.
		  The error produced in this case is: 
		  "Exception: com.fasterxml.jackson.databind.JsonMappingException: No suitable constructor found for type [simple type, class
		   org.apache.kafka.common.securekafkastuff.Read]: can not instantiate from JSON object (need to add/enable type information?) at [Source:
		   (byte[])"{"data":"VALUE97, GROUP1:READ"}"; line: 1, column: 2]" 

		  (ii) If getData() is defined after the constructor, then then the size of the byte array 'data' is 0. The error produced in this case is: 
		  Exception: com.fasterxml.jackson.databind.JsonMappingException: No content to map due to end-of-input at [Source: (byte[])""; line: 1, column: 0]
		  
		  In both cases (i) and (ii), the read object in the ReadDeserializeroo is null.
		  
		  One possible fix was to add a dummy constructor but this did not work with a final variable 

		B) When data is not a final variable 
		   The order of defining the getData() function before or after the constructor does not matter.
		   The size of the byte array 'data' (printed in the App.java terminal) in the ReadDeserializeroo is a positive value (typically 20 after multiple rounds of
		   execution).
		   The error produced in this case is: 
		   " Exception: com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'VALUE94': was expecting ('true', 'false' or 'null') at [Source:
		    (byte[])"VALUE94, GROUP1:READ"; line: 1, column: 9] "
		   	   
	"*/
}
