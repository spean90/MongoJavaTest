package com.hsp.mongojava.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigTool {
	 private static Properties dataBaseProp;
	
	public static Properties getDataBaseConfig(){
		
		if(dataBaseProp==null){
			dataBaseProp = new Properties();
			 InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/database.properties"); 
			 try {
				 dataBaseProp.load(in); 
				  } catch (IOException e) { 
				   e.printStackTrace(); 
				  } 
		}
	   return dataBaseProp;
	}
	
}
