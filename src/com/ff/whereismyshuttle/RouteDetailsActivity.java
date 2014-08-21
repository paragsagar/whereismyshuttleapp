package com.ff.whereismyshuttle;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ff.whereismyshuttle.UserRoutesActivity.RoutesAsyncManager;
import com.ff.whereismyshuttle.domain.Route;
import com.ff.whereismyshuttle.domain.RouteDetail;
import com.ff.whereismyshuttle.domain.RouteDetailsArrayAdapter;
import com.ff.whereismyshuttle.services.JSONServiceManager;

import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class RouteDetailsActivity extends BaseActivity implements LocationListener {

	private ArrayList<RouteDetail> routeDetails = new ArrayList<RouteDetail>();
	private RouteDetailsArrayAdapter<RouteDetail> routesDetailAdapter;
	private ListView lvRouteDetails;
	
	LocationManager locationManager;
//	Geocoder geocoder;
	private String url="completeroutedetails/";
	Button SHOWMAP ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_details);

		int routeId = getIntent().getIntExtra("routeId",1);
		
		url = url+routeId;
		
//		Toast.makeText(this, " URL : "+url, Toast.LENGTH_LONG).show();

		(new RouteDetailAsyncManager()).execute(url);
		
		lvRouteDetails = (ListView)findViewById(R.id.lvRouteDetails);
		routesDetailAdapter = new RouteDetailsArrayAdapter<RouteDetail>(getApplicationContext(), R.layout.route_details_row,routeDetails);
		lvRouteDetails.setAdapter(routesDetailAdapter);

		//Start the location services
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE) ;
//		geocoder = new Geocoder(this);
		
		SHOWMAP = (Button) findViewById(R.id.btnMaps);

		
	}

	public void openInMaps(View v)
	{
		
		Toast.makeText(this, " Opening in Maps -- Please wait: ", Toast.LENGTH_LONG).show();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		MapFragmentClass mf = new MapFragmentClass();
		ft.add(R.id.routeDetailMap, mf);
		ft.commit();
		
	}
	
	public void shareMyLocation(View v)
	{
		Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(lastLocation!=null) 
		{
			onLocationChanged(lastLocation);
				
			lastLocation.getLatitude();
			lastLocation.getLongitude();
			Toast.makeText(this, "Your Shuttle is at this location"+ lastLocation.getLatitude() +" longitude : "+lastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
			//send this location to Server
		}
		else{
			Toast.makeText(this, " longitude : "+lastLocation, Toast.LENGTH_SHORT).show();
		}
			
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

		//send Location to the server
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onResume()
	{
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 10, this);
	}
	
	protected void  onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	
	
	public class RouteDetailAsyncManager extends AsyncTask<String, Void, Boolean> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(RouteDetailsActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Loading Route Details ");
			dialog.show();
			dialog.setCancelable(false);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			String result = "";
			JSONArray jArray = null;
			try {
				HttpClient httpClient = new DefaultHttpClient();
				String uri = JSONServiceManager.BASE_URL+ params[0];
//				HttpPost httpPost = new HttpPost(uri);
				HttpGet httpGet = new HttpGet(uri);
				HttpResponse response = httpClient.execute(httpGet);

				int status = response.getStatusLine().getStatusCode();
				System.out.println("url : "+ uri);
				System.out.println("status : "+ status);
				if (status == 200) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
					
					System.out.println("entity results: "+result);
					
					JSONObject routeObj  = new JSONObject(result);
					jArray = routeObj.getJSONArray("routeStops");
				}
				else 
				{
					result = getJsonStringFromCache();
				}

				JSONObject jsonObject = null;
				RouteDetail busStop = null;

				for (int i = 0; i < jArray.length(); i++) {
					jsonObject = jArray.getJSONObject(i);
					busStop = new RouteDetail();
					busStop.setStopName(jsonObject.getString("stopName"));
					busStop.setStopDetails(jsonObject.getString("stopDetails"));
					busStop.setEta(jsonObject.getString("cta"));
					busStop.setSta(jsonObject.getString("eta"));
					busStop.setLatitude(jsonObject.getString("locationLat"));
					busStop.setLongitude(jsonObject.getString("locationLon"));
//					busStop.setNoOfCheckins(jsonObject.getString("checkins"));
					routeDetails.add(busStop);
				}
				return true;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			return false;
		}
		
		private String getJsonStringFromCache() {
			// TODO Auto-generated method stub
			 String data = " [{\"routeDetailsId\":1,\"routeName\":\"Peninsula Shuttle Schedule 1st Pickup\",\"startTime\":\"6:30 AM\",\"endTime\":\"7:45 AM\",\"routeStops\":[{\"currentStop\":{\"stopName\":\"Daly City Bart\",\"stopDetails\":\"Daly City Bart\",\"locationLat\":37.71581,\"locationLon\":-122.456808,\"totalCheckin\":0},\"eta\":\"6:30 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 2\",\"stopDetails\":\"AAA Sign on Serravista \u0026 Gellert, Daly City\",\"locationLat\":37.687924,\"locationLon\":-122.470208,\"totalCheckin\":0},\"eta\":\"6:45 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 3 \",\"stopDetails\":\"101/92 Park and Ride, San Mateo\",\"locationLat\":37.552278,\"locationLon\":-122.298324,\"totalCheckin\":0},\"eta\":\"7:15 AM\"},{\"currentStop\":{\"stopName\":\"CA Office\",\"stopDetails\":\"3965, Freedom Circle, Santa Clara, CA\",\"locationLat\":37.385795,\"locationLon\":-121.97313,\"totalCheckin\":0},\"eta\":\"7:45 AM\"}]},{\"routeDetailsId\":2,\"routeName\":\"Peninsula Shuttle Schedule 2nd Pickup\",\"startTime\":\"8:50 AM\",\"endTime\":\"10:00 AM\",\"routeStops\":[{\"currentStop\":{\"stopName\":\"Daly City Stop 2\",\"stopDetails\":\"AAA Sign on Serravista \u0026 Gellert, Daly City\",\"locationLat\":37.687924,\"locationLon\":-122.470208,\"totalCheckin\":0},\"eta\":\"8:50 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 3 \",\"stopDetails\":\"101/92 Park and Ride, San Mateo\",\"locationLat\":37.552278,\"locationLon\":-122.298324,\"totalCheckin\":0},\"eta\":\"9:20 AM\"},{\"currentStop\":{\"stopName\":\"CA Office\",\"stopDetails\":\"3965, Freedom Circle, Santa Clara, CA\",\"locationLat\":37.385795,\"locationLon\":-121.97313,\"totalCheckin\":0},\"eta\":\"10:00 AM\"}]},{\"routeDetailsId\":3,\"routeName\":\"Peninsula Shuttle Schedule Evening\",\"startTime\":\"4:00 PM\",\"endTime\":\"6:00 PM\",\"routeStops\":[{\"currentStop\":{\"stopName\":\"CA Office\",\"stopDetails\":\"3965, Freedom Circle, Santa Clara, CA\",\"locationLat\":37.385795,\"locationLon\":-121.97313,\"totalCheckin\":0},\"eta\":\"4:00 PM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 3 \",\"stopDetails\":\"101/92 Park and Ride, San Mateo\",\"locationLat\":37.552278,\"locationLon\":-122.298324,\"totalCheckin\":0},\"eta\":\"4:40 PM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 2\",\"stopDetails\":\"AAA Sign on Serravista \u0026 Gellert, Daly City\",\"locationLat\":37.687924,\"locationLon\":-122.470208,\"totalCheckin\":0},\"eta\":\"5:10 PM\"},{\"currentStop\":{\"stopName\":\"Daly City Bart\",\"stopDetails\":\"Daly City Bart\",\"locationLat\":37.71581,\"locationLon\":-122.456808,\"totalCheckin\":0},\"eta\":\"5:25 PM\"},{\"currentStop\":{\"stopName\":\"SFO Stop1\",\"stopDetails\":\"26th and Guerrero St,SFO\",\"locationLat\":37.748777,\"locationLon\":-122.422626,\"totalCheckin\":0},\"eta\":\"5:45 PM\"},{\"currentStop\":{\"stopName\":\"SFO Stop 2\",\"stopDetails\":\"Market and Spear, SFO\",\"locationLat\":37.793816,\"locationLon\":-122.395682,\"totalCheckin\":0},\"eta\":\"6:00 PM\"}]},{\"routeDetailsId\":4,\"routeName\":\"San Francisco Shuttle Schedule (Morning)\",\"startTime\":\"7:00 AM\",\"endTime\":\"9:00 AM\",\"routeStops\":[{\"currentStop\":{\"stopName\":\"SFO Stop 2\",\"stopDetails\":\"Market and Spear, SFO\",\"locationLat\":37.793816,\"locationLon\":-122.395682,\"totalCheckin\":0},\"eta\":\"7:00 AM\"},{\"currentStop\":{\"stopName\":\"SFO Stop1\",\"stopDetails\":\"26th and Guerrero St,SFO\",\"locationLat\":37.748777,\"locationLon\":-122.422626,\"totalCheckin\":0},\"eta\":\"7:20 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 2\",\"stopDetails\":\"AAA Sign on Serravista \u0026 Gellert, Daly City\",\"locationLat\":37.687924,\"locationLon\":-122.470208,\"totalCheckin\":0},\"eta\":\"7:40 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 3 \",\"stopDetails\":\"101/92 Park and Ride, San Mateo\",\"locationLat\":37.552278,\"locationLon\":-122.298324,\"totalCheckin\":0},\"eta\":\"8:15 AM\"},{\"currentStop\":{\"stopName\":\"CA Office\",\"stopDetails\":\"3965, Freedom Circle, Santa Clara, CA\",\"locationLat\":37.385795,\"locationLon\":-121.97313,\"totalCheckin\":0},\"eta\":\"9:00 AM\"}]}]";
			return data;
		}

		@Override
		protected void onPostExecute(Boolean result){
			super.onPostExecute(result);
			dialog.cancel();
			if(!result)
			{
				Toast.makeText(getApplicationContext(), "Error: Cant Load from Server !", Toast.LENGTH_LONG).show();
			}
			else
			{

			}

			
			routesDetailAdapter.notifyDataSetChanged();			

		}

	}	
}
