package com.ff.whereismyshuttle.domain;

	public class RouteDetail{
		
		
		private String stopName;
		private String stopDetails;
		private String eta;
		private String sta;
		private String noOfCheckins;

		private String latitude;
		private String longitude;
		
		public String getStopName() {
			return stopName;
		}

		public void setStopName(String stopName) {
			this.stopName = stopName;
		}

		public String getStopDetails() {
			return stopDetails;
		}

		public void setStopDetails(String stopDetails) {
			this.stopDetails = stopDetails;
		}


		public String getEta() {
			return eta;
		}

		public void setEta(String eta) {
			this.eta = eta;
		}

		public String getSta() {
			return sta;
		}

		public void setSta(String sta) {
			this.sta = sta;
		}

		public String getNoOfCheckins() {
			return noOfCheckins;
		}

		public void setNoOfCheckins(String noOfCheckins) {
			this.noOfCheckins = noOfCheckins;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}		
	}
