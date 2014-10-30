package com.hsp.mongojava.util;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

public class MongoManager {
	
	private static Mongo mongo;
	private MongoManager(){
		
	}
	public static DB getDB(String name){
		if(mongo==null){
			init();
		}
		String username = ConfigTool.getDataBaseConfig().getProperty("username");
		String password = ConfigTool.getDataBaseConfig().getProperty("password");
		DB db = mongo.getDB(name);
		if(db.authenticate(username, password.toCharArray())){
			return db;
		}
		return db;
	}
	private static void init() {
		String host = ConfigTool.getDataBaseConfig().getProperty("host");
		int port = Integer.parseInt(ConfigTool.getDataBaseConfig().getProperty("port"));
		int poolSize = Integer.parseInt(ConfigTool.getDataBaseConfig().getProperty("poolSize"));
		int blockSize = Integer.parseInt(ConfigTool.getDataBaseConfig().getProperty("blockSize"));
		System.out.println("poolSize: "+poolSize+"  blockSize: "+blockSize);
		try {
			mongo = new Mongo(host, port);
			MongoOptions mongoOptions = mongo.getMongoOptions();
			mongoOptions.setConnectionsPerHost(poolSize);
			mongoOptions.setThreadsAllowedToBlockForConnectionMultiplier(blockSize);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
