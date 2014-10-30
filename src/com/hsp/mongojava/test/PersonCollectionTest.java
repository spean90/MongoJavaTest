package com.hsp.mongojava.test;

import java.util.List;

import com.hsp.mongojava.util.MongoManager;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PersonCollectionTest {

	public static void main(String[] args) {
		DB db = MongoManager.getDB("demodb");
		DBCollection coll = db.getCollection("person");
		DBCursor cur = coll.find();
		System.out.println(cur.toString());
		System.out.println(cur.toArray());
		List<DBObject> list = cur.toArray();
		System.out.println(list.get(0));
		DBObject dbo = list.get(0);
		dbo.put("age", 24);
		System.out.println(dbo.toString());
		coll.save(dbo);
		System.out.println(coll.find(dbo).toArray());
		
	}
}
