package com.ff.whereismyshuttle.domain;

import java.util.ArrayList;
import com.ff.whereismyshuttle.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class AllRouteArrayAdapter<T> extends ArrayAdapter<Route> {
	
	ArrayList<Route> routeList = new ArrayList<Route>();
	int resource;
	Context context;
	LayoutInflater layout;
	
	

	public AllRouteArrayAdapter(Context context, int resource, ArrayList<Route> objects) {
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
			
			holder.tvRouteId = (TextView) v.findViewById(R.id.tvAllRouteId);
			holder.cbRouteId = (CheckBox) v.findViewById(R.id.cbRouteId);
			holder.tvRouteName = (TextView) v.findViewById(R.id.tvAllRouteName);

			v.setTag(holder);

			holder.cbRouteId.setOnClickListener( new View.OnClickListener() {  
		        public void onClick(View v) {  
		         CheckBox cb = (CheckBox) v ;  
		         Route route = (Route) cb.getTag();  
//		         Toast.makeText(context,"Clicked on Checkbox: " + cb.getText() +" is " + cb.isChecked(),Toast.LENGTH_LONG).show();
		         route.setSelected(cb.isChecked());
		        }
		       });  

		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		Route route = routeList.get(position);
		

		holder.tvRouteId.setText( ""+ routeList.get(position).getRouteId());
		holder.tvRouteName.setText(routeList.get(position).getRouteName());

		
	   holder.cbRouteId.setText(route.getRouteName());
	   holder.cbRouteId.setChecked(route.isSelected());
	   holder.cbRouteId.setTag(route);
		   
		return v;		
	}

	
	static class ViewHolder{
		
		public TextView tvRouteId;
		public CheckBox cbRouteId; 
		public TextView tvRouteName;
		
	}
}
