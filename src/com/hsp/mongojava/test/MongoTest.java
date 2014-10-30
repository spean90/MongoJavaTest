package com.hsp.mongojava.test;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoTest {

	
	public static void main(String[] args) {
		/**
		 * 准备操作：安装mongdb --启动 默认端口27017； 
		 * 创建demodb collection
		 * 创建test并添加数据 我这边添加了50w条  name,class,age
		 */
		
		Mongo mongo = null;
		try {
			mongo = new Mongo("127.0.0.1", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		DB db = mongo.getDB("demodb");
		 boolean auth = db.authenticate("hsp", "hsp".toCharArray()); //默认不需要权限验证；
		 System.out.println(auth);
		 DBCollection test = db.getCollection("test");
		 /**
		  * 每个BasicDBObject 相当于shell里的一个{}
		  */
		 DBCursor c = test.find(new BasicDBObject().append("name", new BasicDBObject("$lt", "user_300000").append("$gt", "user_200000")));
		/**
		 * 上行代码想到于shell:
		 * db.test.find({'name':{'$lt':'user_300000','$gt':'user_200000'}})
		 * 可以分开写成
		 * DBObject d1 = new DBOject();
		 * d1.append("$lt", "user_300000");
		 * d1.append("$gt", "user_200000");
		 * DBObject d2 = new DBOject("name":d1);  
		 */
		 System.out.println(c.count());   //db.test.find().count();
		 System.out.println(c.next());   //通过游标获取数据。。
		 /**
		  * 主要的分组操作
		  * 分组主要有四个比较重要的参数；
		  * key-使用什么分组，格式：key:{'XX':true,'XX'true,...}  xx为列。可以有多个；
		  * initial: 初始化参数； 如下需要统计人数,则定义一个amount计数器；
		  * reduce:操作分组数据的function(doc,pre);   //比如你要统计每组年龄总和 可以function(doc,pre){ pre.amount+=doc.age;} //amount就为年龄总和
		  * condition:分组条件；筛选参加分组的文档；
		  * 这样就基本可以实现mysql那样的分组了；
		  */
		 String func ="function(doc,pre){ pre.amount++;}";
		 DBObject d = test.group(new BasicDBObject("class",true), null, new BasicDBObject("amount",0) , func);
		 System.out.println(d.toString());
		List<DBObject> list = (List<DBObject>) d;
		System.out.println(list.size());
		System.out.println(list.get(0).get("class"));
		
		//**
		// * 模糊查询  正则表达式
		// * 如下代码相当于shell:
		// db.test.find({name:/.*user_4.*/});   mysql：%user_4%;
		// */
		 Pattern p = Pattern.compile(".*user_4.*");
		DBCursor c2 = test.find(new BasicDBObject("name",p)); 
		System.out.println(c2.toString());
		System.out.println(c2.count());
		
		
	}
}
