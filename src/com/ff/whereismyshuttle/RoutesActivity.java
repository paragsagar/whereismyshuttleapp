package com.ff.whereismyshuttle;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RoutesActivity extends BaseActivity {
	
	private ArrayList<String> routes;
	private ArrayAdapter<String> routesAdapter;
	private ListView lvRoutes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routes);
		lvRoutes = (ListView)findViewById(R.id.lvRoutes);
		populateRoutesArrayItems();

		routesAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,routes);
		lvRoutes.setAdapter(routesAdapter);
		setupItemListListeners();
	}
	
	private void setupItemListListeners() {

		lvRoutes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int position,long id) 
			{
				Intent i = new Intent(RoutesActivity.this, RouteDetailsActivity.class);
				startActivity(i);
			}
		});

	}	

	private void populateRoutesArrayItems() {
		routes = new ArrayList<String>();
		routes.add("SFO-SAN MATEO-SANTA CLARA Route 1");
		routes.add("SFO-SAN MATEO-SANTA CLARA Route 2");
		routes.add("UNION CITY-FREMONT-SANTA CLARA Route 4");
	}

}
