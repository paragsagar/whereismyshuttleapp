package com.ff.whereismyshuttle.domain;

import java.util.ArrayList;
import com.ff.whereismyshuttle.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RouteDetailsArrayAdapter<T> extends ArrayAdapter<RouteDetail> {
	
	ArrayList<RouteDetail> RouteDetailsList = new ArrayList<RouteDetail>();
	int resource;
	Context context;
	LayoutInflater layout;
	
	

	public RouteDetailsArrayAdapter(Context context, int resource, ArrayList<RouteDetail> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		RouteDetailsList = objects;
		
		layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		StopViewHolder holder;	
		View v = convertView;
		if (v == null) {
			holder = new StopViewHolder();
			v = layout.inflate(resource, null);
			holder.tvStopId = (TextView) v.findViewById(R.id.tvStopId);
			holder.tvStopName = (TextView) v.findViewById(R.id.tvStopName);
			
			holder.tvStopDetails = (TextView) v.findViewById(R.id.tvStopDetails);
			holder.tvEta = (TextView) v.findViewById(R.id.tvEta);
			holder.tvSta = (TextView) v.findViewById(R.id.tvSta);
			holder.tvNoOfCheckins = (TextView) v.findViewById(R.id.tvCheckins);
			
			holder.tvLatitude = (TextView) v.findViewById(R.id.tvLatitude);
			holder.tvLongitude = (TextView) v.findViewById(R.id.tvLongitude);

			v.setTag(holder);
		} else {
			holder = (StopViewHolder) v.getTag();
		}
		

		holder.tvStopName.setText(RouteDetailsList.get(position).getStopName());
		
		holder.tvStopDetails.setText( RouteDetailsList.get(position).getStopDetails());
		holder.tvEta.setText("Estimated Time :" + RouteDetailsList.get(position).getEta());
		holder.tvSta.setText("Scheduled Time :"+ RouteDetailsList.get(position).getSta());
		holder.tvNoOfCheckins.setText(RouteDetailsList.get(position).getNoOfCheckins() != null ? RouteDetailsList.get(position).getNoOfCheckins() : 0 +" Users Checked in the bus!");

		holder.tvLatitude.setText( RouteDetailsList.get(position).getLatitude());
		holder.tvLongitude.setText(RouteDetailsList.get(position).getLongitude());
		
		return v;		
	}

	
	static class StopViewHolder{
		
		public TextView tvStopId;
		public TextView tvStopName;
		public TextView tvStopDetails;
		public TextView tvEta;
		public TextView tvSta;
		public TextView tvNoOfCheckins;		
		public TextView tvLatitude;
		public TextView tvLongitude;
		
		
	}
}
