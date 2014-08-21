package com.ff.whereismyshuttle.services;


public class JSONServiceManager{
	
	public static String BASE_URL = "http://192.168.1.4:9000/api/v1/User/";
	public static JSONServiceManager getInstance()
	{
		return new JSONServiceManager();
	}
}
