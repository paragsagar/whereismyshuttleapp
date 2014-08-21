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

import com.ff.whereismyshuttle.domain.AllRouteArrayAdapter;
import com.ff.whereismyshuttle.domain.Route;
import com.ff.whereismyshuttle.domain.RouteArrayAdapter;
import com.ff.whereismyshuttle.services.JSONServiceManager;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;

public class RoutesActivity extends BaseActivity {

	private ArrayList<Route> routes;
	private ArrayList<String> userRoutes;
	private AllRouteArrayAdapter<Route> routesAdapter;
	private ListView lvRoutes;
	private CheckBox cbRoutes;
	private String url = "completeroutedetails";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routes);
		routes = new ArrayList<Route>();
		lvRoutes = (ListView) findViewById(R.id.lvRoutes);
		cbRoutes = (CheckBox) findViewById(R.id.cbRouteId);
		// Async call to load;
		(new AllRoutesAsyncManager()).execute(url);
		routesAdapter = new AllRouteArrayAdapter<Route>(
				getApplicationContext(), R.layout.all_route_row, routes);
		lvRoutes.setAdapter(routesAdapter);

		setupItemListListeners();
	}

	public void processDone(View v)
	{
		Intent i = new Intent(RoutesActivity.this,UserRoutesActivity.class);
		startActivity(i);
	}
	// Method on the button Click
	public void addToUserRoutes(View v) {


		 
	    StringBuffer responseText = new StringBuffer();
	    responseText.append("The following were added...\n");
	 
	    ArrayList<Route> countryList = routes;
	    for(int i=0;i<countryList.size();i++){
	     Route route = countryList.get(i);
	     if(route.isSelected()){
	      responseText.append("\n" + route.getRouteName());
	     }
	    }
	 
	    Toast.makeText(getApplicationContext(),responseText, Toast.LENGTH_LONG).show();
	}

	private void setupItemListListeners() {

		lvRoutes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item,
					int position, long id) {
				Intent i = new Intent(RoutesActivity.this,RouteDetailsActivity.class);
				i.putExtra("routeId", routes.get(position).getRouteId());
				startActivity(i);
			}
		});
		


	}

	public class AllRoutesAsyncManager extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(RoutesActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			String result = "";
			JSONArray jArray = null;
			try {
				HttpClient httpClient = new DefaultHttpClient();
				String uri = JSONServiceManager.BASE_URL + params[0];
				// HttpPost httpPost = new HttpPost(uri);
				HttpGet httpGet = new HttpGet(uri);
				HttpResponse response = httpClient.execute(httpGet);

				int status = response.getStatusLine().getStatusCode();
				System.out.println("url : " + uri);
				System.out.println("status : " + status);
				if (status == 200) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);

					System.out.println("entity results: " + result);

					jArray = new JSONArray(result);
				} else {
					result = getJsonStringFromCache();
				}

				JSONObject jsonObject = null;
				Route route = null;

				for (int i = 0; i < jArray.length(); i++) {
					jsonObject = jArray.getJSONObject(i);
					route = new Route();
					route.setRouteName(jsonObject.getString("routeName"));
					route.setRouteId(jsonObject.getInt("routeDetailsId"));
					routes.add(route);
				}
				return true;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				dialog.cancel();
			}
			return false;
		}

		private String getJsonStringFromCache() {
			// TODO Auto-generated method stub
			String data = " [{\"routeDetailsId\":1,\"routeName\":\"Peninsula Shuttle Schedule 1st Pickup\",\"startTime\":\"6:30 AM\",\"endTime\":\"7:45 AM\",\"routeStops\":[{\"currentStop\":{\"stopName\":\"Daly City Bart\",\"stopDetails\":\"Daly City Bart\",\"locationLat\":37.71581,\"locationLon\":-122.456808,\"totalCheckin\":0},\"eta\":\"6:30 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 2\",\"stopDetails\":\"AAA Sign on Serravista \u0026 Gellert, Daly City\",\"locationLat\":37.687924,\"locationLon\":-122.470208,\"totalCheckin\":0},\"eta\":\"6:45 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 3 \",\"stopDetails\":\"101/92 Park and Ride, San Mateo\",\"locationLat\":37.552278,\"locationLon\":-122.298324,\"totalCheckin\":0},\"eta\":\"7:15 AM\"},{\"currentStop\":{\"stopName\":\"CA Office\",\"stopDetails\":\"3965, Freedom Circle, Santa Clara, CA\",\"locationLat\":37.385795,\"locationLon\":-121.97313,\"totalCheckin\":0},\"eta\":\"7:45 AM\"}]},{\"routeDetailsId\":2,\"routeName\":\"Peninsula Shuttle Schedule 2nd Pickup\",\"startTime\":\"8:50 AM\",\"endTime\":\"10:00 AM\",\"routeStops\":[{\"currentStop\":{\"stopName\":\"Daly City Stop 2\",\"stopDetails\":\"AAA Sign on Serravista \u0026 Gellert, Daly City\",\"locationLat\":37.687924,\"locationLon\":-122.470208,\"totalCheckin\":0},\"eta\":\"8:50 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 3 \",\"stopDetails\":\"101/92 Park and Ride, San Mateo\",\"locationLat\":37.552278,\"locationLon\":-122.298324,\"totalCheckin\":0},\"eta\":\"9:20 AM\"},{\"currentStop\":{\"stopName\":\"CA Office\",\"stopDetails\":\"3965, Freedom Circle, Santa Clara, CA\",\"locationLat\":37.385795,\"locationLon\":-121.97313,\"totalCheckin\":0},\"eta\":\"10:00 AM\"}]},{\"routeDetailsId\":3,\"routeName\":\"Peninsula Shuttle Schedule Evening\",\"startTime\":\"4:00 PM\",\"endTime\":\"6:00 PM\",\"routeStops\":[{\"currentStop\":{\"stopName\":\"CA Office\",\"stopDetails\":\"3965, Freedom Circle, Santa Clara, CA\",\"locationLat\":37.385795,\"locationLon\":-121.97313,\"totalCheckin\":0},\"eta\":\"4:00 PM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 3 \",\"stopDetails\":\"101/92 Park and Ride, San Mateo\",\"locationLat\":37.552278,\"locationLon\":-122.298324,\"totalCheckin\":0},\"eta\":\"4:40 PM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 2\",\"stopDetails\":\"AAA Sign on Serravista \u0026 Gellert, Daly City\",\"locationLat\":37.687924,\"locationLon\":-122.470208,\"totalCheckin\":0},\"eta\":\"5:10 PM\"},{\"currentStop\":{\"stopName\":\"Daly City Bart\",\"stopDetails\":\"Daly City Bart\",\"locationLat\":37.71581,\"locationLon\":-122.456808,\"totalCheckin\":0},\"eta\":\"5:25 PM\"},{\"currentStop\":{\"stopName\":\"SFO Stop1\",\"stopDetails\":\"26th and Guerrero St,SFO\",\"locationLat\":37.748777,\"locationLon\":-122.422626,\"totalCheckin\":0},\"eta\":\"5:45 PM\"},{\"currentStop\":{\"stopName\":\"SFO Stop 2\",\"stopDetails\":\"Market and Spear, SFO\",\"locationLat\":37.793816,\"locationLon\":-122.395682,\"totalCheckin\":0},\"eta\":\"6:00 PM\"}]},{\"routeDetailsId\":4,\"routeName\":\"San Francisco Shuttle Schedule (Morning)\",\"startTime\":\"7:00 AM\",\"endTime\":\"9:00 AM\",\"routeStops\":[{\"currentStop\":{\"stopName\":\"SFO Stop 2\",\"stopDetails\":\"Market and Spear, SFO\",\"locationLat\":37.793816,\"locationLon\":-122.395682,\"totalCheckin\":0},\"eta\":\"7:00 AM\"},{\"currentStop\":{\"stopName\":\"SFO Stop1\",\"stopDetails\":\"26th and Guerrero St,SFO\",\"locationLat\":37.748777,\"locationLon\":-122.422626,\"totalCheckin\":0},\"eta\":\"7:20 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 2\",\"stopDetails\":\"AAA Sign on Serravista \u0026 Gellert, Daly City\",\"locationLat\":37.687924,\"locationLon\":-122.470208,\"totalCheckin\":0},\"eta\":\"7:40 AM\"},{\"currentStop\":{\"stopName\":\"Daly City Stop 3 \",\"stopDetails\":\"101/92 Park and Ride, San Mateo\",\"locationLat\":37.552278,\"locationLon\":-122.298324,\"totalCheckin\":0},\"eta\":\"8:15 AM\"},{\"currentStop\":{\"stopName\":\"CA Office\",\"stopDetails\":\"3965, Freedom Circle, Santa Clara, CA\",\"locationLat\":37.385795,\"locationLon\":-121.97313,\"totalCheckin\":0},\"eta\":\"9:00 AM\"}]}]";
			return data;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dialog.cancel();
			if (!result) {
				Toast.makeText(getApplicationContext(),
						"Cant Load from Server, Loading from Cache !",
						Toast.LENGTH_SHORT).show();
			} else {

			}

			routesAdapter.notifyDataSetChanged();
		}
	}
}
