package com.ff.whereismyshuttle.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ff.whereismyshuttle.UserRoutesActivity.RoutesAsyncManager;
import com.ff.whereismyshuttle.services.JSONServiceManager;

import android.os.AsyncTask;

public class User {
	private static final String WHEREISMYSHUTTLE_TXT_FILE = "whereismyshuttle.txt";
	private static String email;
	private boolean notifications;
	private String fullName;
	
	
	public static String getEmail(File fileDir) {
		if(email==null || email.length()<=0)
		{
			email = readFromFile(fileDir);
		}
		return email;
	}
	
	
	private static String readFromFile(File fileDir) {
		String email = "";

		File appFile = new File(fileDir,WHEREISMYSHUTTLE_TXT_FILE);
		try{
			email = FileUtils.readFileToString(appFile);
		}catch (IOException ioe){
			// error
		}		
		return email;
	}


	public void setEmail(String email_,File fileDir) {
		email = email_;
		String url = "adduser";
		writeToFile(email_,fileDir);
		UserAsyncManager sync = new UserAsyncManager();
		sync.execute(url,email_);

	}
	
	/**
	 * method Write to the Text file (first version of App)
	 */
	private static void writeToFile(String email,File fileDir) {
		File appFile = new File(fileDir,WHEREISMYSHUTTLE_TXT_FILE);
		try{
			FileUtils.write(appFile, email,false);
		}catch (Exception ioe){
			ioe.printStackTrace();
		}
	}

	public class UserAsyncManager extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			String result = "";
			JSONArray jArray = null;
			try {
				HttpClient httpClient = new DefaultHttpClient();
				String uri = JSONServiceManager.BASE_URL+ params[0];
				HttpPost httpPost = new HttpPost(uri);
	//			HttpGet httpGet = new HttpGet(uri);
				httpPost.setHeader("content-type", "application/json");
				List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
                // add an HTTP variable and value pair
				nameValuePairs.add(new BasicNameValuePair("myHttpData", params[1]));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse response = httpClient.execute(httpPost);
	
				int status = response.getStatusLine().getStatusCode();
				System.out.println("url : "+ uri);
				System.out.println("status : "+ status);
				if (status == 200) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
					
					System.out.println("entity results: "+result);
					
					jArray = new JSONArray(result);
				}
	
	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	
			}
			return false;
		}	
	}

}
