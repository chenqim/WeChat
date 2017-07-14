package com.wx.po.weather;

public class Results {
	private Location location;

	private Daily[] daily ;

	private String last_update;

	public void setLocation(Location location){
		this.location = location;
	}
	public Location getLocation(){
		return this.location;
	}
	
	public Daily[] getDaily() {
		return daily;
	}
	public void setDaily(Daily[] daily) {
		this.daily = daily;
	}
	public void setLast_update(String last_update){
		this.last_update = last_update;
	}
	public String getLast_update(){
		return this.last_update;
	}

}
