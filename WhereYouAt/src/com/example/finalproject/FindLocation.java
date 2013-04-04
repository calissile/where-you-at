/************************************************************************/
/*																		*/
/*		BumpTest.java													*/
/*		Implemented by Kevin Sounthavong								*/
/*		Edited by Kevin Sounthavong, Lee Zhou							*/
/*																		*/
/*																		*/
/************************************************************************/

package com.example.finalproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class FindLocation extends Service implements LocationListener {
	
	Context mContext;
	Double latitude,longitude;
	public LocationManager locationManager;
	public LocationListener locationListener;
	Location location;


	public FindLocation(Context context)
	{
		this.mContext=context;
		getLocation();
	}
		public Location getLocation()
		{
		//Get reference to Location Manager
		locationManager=(LocationManager) mContext.getSystemService(LOCATION_SERVICE);
		
		//Ask for location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
		
		if(locationManager!=null)
		{
			//Retrieves Last Location you were at
			location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
			if(location!=null)
			{
				latitude=location.getLatitude();
				longitude=location.getLongitude();
				
				Log.d("Lat",latitude.toString());
				Log.d("Long",longitude.toString());
			}
		}
		
		return location;
		}

		//Returns Latitude. Should return 0.0 if location = null
	public double getLat()
	{
		if(location!=null)
		{
			latitude=location.getLatitude();
		}
		return latitude;
	}
	
	//Returns Longitude. Should return 0.0 if location = null
	public double getLong()
	{
		if(location!=null)
		{
			longitude=location.getLongitude();
		}
		return longitude;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public void onLocationChanged(Location location) {
		if(location!=null)
		{
			latitude=location.getLatitude();
			longitude=location.getLongitude();
		}
		
		
		
	}
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
