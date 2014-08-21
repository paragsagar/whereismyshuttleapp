package com.ff.whereismyshuttle.domain;

import java.util.ArrayList;
import com.ff.whereismyshuttle.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RouteArrayAdapter<T> extends ArrayAdapter<Route> {
	
	ArrayList<Route> routeList = new ArrayList<Route>();
	int resource;
	Context context;
	LayoutInflater layout;
	
	

	public RouteArrayAdapter(Context context, int resource, ArrayList<Route> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		routeList = objects;
		
		layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;	
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = layout.inflate(	resource,parent,false);
			
			holder.tvRouteId = (TextView) v.findViewById(R.id.tvRouteId);
			holder.tvRouteName = (TextView) v.findViewById(R.id.tvRouteName);

			v.setTag(holder);
			System.out.println(" v was null:"+ holder);
		} else {
			holder = (ViewHolder) v.getTag();
			System.out.println(" v was not null:"+ holder);
		}

		if(holder != null ){
//			holder.tvRouteId = (TextView) v.findViewById(R.id.tvRouteId);
//			holder.tvRouteName = (TextView) v.findViewById(R.id.tvRouteName);
		}
		
		holder.tvRouteId.setText( ""+ routeList.get(position).getRouteId());
		holder.tvRouteName.setText(routeList.get(position).getRouteName());
		return v;		
	}

	
	static class ViewHolder{
		
		public TextView tvRouteId;
		public TextView tvRouteName;
		
	}
}
