package com.ff.whereismyshuttle.domain;

	public class Route{
		
		private String routeName;
		private int routeId;
		private boolean isSelected;
		
		public String getRouteName() {
			return routeName;
		}

		public void setRouteName(String routeName) {
			this.routeName = routeName;
		}

		public int getRouteId() {
			return routeId;
		}

		public void setRouteId(int routeId) {
			this.routeId = routeId;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}		
	}
