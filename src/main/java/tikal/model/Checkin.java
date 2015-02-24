package tikal.model;

import java.io.Serializable;import java.lang.String;

public class Checkin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	double latitude;
	double longitude;
	String userId;
	long timestamp;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString(){
		return userId+": lat:"+latitude+" lng:"+longitude+" :: "+timestamp;
	}
	
	
}
