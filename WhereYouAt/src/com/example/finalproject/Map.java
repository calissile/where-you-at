/************************************************************************/
/*																		*/
/*		Map.java														*/
/*		Implemented by Kevin Sounthavong								*/
/*		Edited by Kevin Sounthavong, Lee Zhou							*/
/*																		*/
/*																		*/
/************************************************************************/

package com.example.finalproject;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Map extends MapActivity{
	
	MapView mapView;
	MapController mc;
	GeoPoint gp, fGP;
	List<Overlay> mapOverlays;
	OverlayItem oItem;
	MyItemizedOverlay iOverlay;
	double latitude,longitude;
	double user_lat,user_long;
	double friendLat,friendLong;
	String userName,friendName;
	//Nearest Places
	PlacesList nearPlaces;
	

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.map);
		
				
		mapView=(MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		
		mc=mapView.getController();
		
		Intent i=getIntent();
		
		if (i!=null)
		{
		//Receive User's Lat and Long	
		user_lat=i.getDoubleExtra("userLAT", 0);
		user_long=i.getDoubleExtra("userLONG",0);
		
		//Receive Friend's Lat and Long
		
		friendLat=i.getDoubleExtra("friendLAT", 0);
		friendLong=i.getDoubleExtra("friendLong",0);
		
		
		//Obtain nearPlaces List from Main Activity
		nearPlaces=(PlacesList) i.getSerializableExtra("userNearPlaces");
		
		userName=i.getStringExtra("userNAME");
	
		friendName=i.getStringExtra("friendNAME");
		Log.d("Map", userName+ " " +user_lat + " " + user_long);
    	Log.d("Map2", friendName+ " " + friendLat + " " + friendLat);
		gp=new GeoPoint((int)(user_lat*1E6),(int)(user_long*1E6));
		
		//Friend's GeoPoint
		fGP=new GeoPoint((int)(friendLat*1E6),(int)(friendLong*1E6));
		
		}
		
		mapOverlays=mapView.getOverlays();
		Drawable marker=this.getResources().getDrawable(R.drawable.you);
		
		
		iOverlay=new MyItemizedOverlay(marker, this);
		oItem=new OverlayItem(gp,"Your Location, "+userName,"This is you!");
		
		iOverlay.addOVerlay(oItem);
		mapOverlays.add(iOverlay);
		
		
		Drawable fMarker=this.getResources().getDrawable(R.drawable.smiley);
		
		iOverlay=new MyItemizedOverlay(fMarker,this);
		
		//Add friend marker to the map
		oItem=new OverlayItem(fGP,friendName+"'s Location", "This is your friend");
		iOverlay.addOVerlay(oItem);
		mapOverlays.add(iOverlay);
		
        //Toast.makeText(getBaseContext(), "nearPlaces is "+nearPlaces.results.isEmpty(), Toast.LENGTH_SHORT).show();

       
        //Check for null nearPlaces
        if(nearPlaces.results!=null)
        {
        	//Loop through all places
        	for(Place place:nearPlaces.results)
        	{
        		
        		latitude=place.geometry.location.lat;
        		longitude=place.geometry.location.lng;
        		
        		for(String s:place.types)
        		{
        			if(s.contains("restaurant"))
        			{
        				Drawable rMarker=this.getResources().getDrawable(R.drawable.restaurant);
        				
        				iOverlay=new MyItemizedOverlay(rMarker,this);
        				
        				//GeoPoint to place on map
        				gp=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
        				
        				//Map overlay item
        				oItem=new OverlayItem(gp, place.name, place.vicinity);
        				
        				iOverlay.addOVerlay(oItem);
        				
        				iOverlay.addOVerlay(oItem);
        				mapOverlays.add(iOverlay);
        			}
        			
        			if((s.contains("cafe"))||place.name.contains("cafe"))
        			{
        				Drawable cafeMarker=this.getResources().getDrawable(R.drawable.cafe);
        		        
        		        iOverlay=new MyItemizedOverlay(cafeMarker,this);
        		        //GeoPoint to place on map
        		        gp=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
        		        
        		        //Map overlay item
        		        oItem=new OverlayItem(gp, place.name, place.vicinity);
        		        
        		        iOverlay.addOVerlay(oItem);
        		        
        		        iOverlay.addOVerlay(oItem);
        	        	mapOverlays.add(iOverlay);
        	        	
        			}
        			
        			else if((s.contains("liquor_store"))||place.name.contains("liquors"))
        			{
        				Drawable barMarker=this.getResources().getDrawable(R.drawable.bar);
        		        
        		        iOverlay=new MyItemizedOverlay(barMarker,this);
        		        //GeoPoint to place on map
        		        gp=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
        		        
        		        //Map overlay item
        		        oItem=new OverlayItem(gp, place.name, place.vicinity);
        		        
        		        iOverlay.addOVerlay(oItem);
        		        
        		        iOverlay.addOVerlay(oItem);
        	        	mapOverlays.add(iOverlay);
        	        	
        			}
        			
        			else if(s.contains("department_store")||s.contains("clothing_store")||s.contains("convenience_store"))
        			{
        				Drawable shopMarker=this.getResources().getDrawable(R.drawable.mall);
        		        
        		        iOverlay=new MyItemizedOverlay(shopMarker,this);
        		        //GeoPoint to place on map
        		        gp=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
        		        
        		        //Map overlay item
        		        oItem=new OverlayItem(gp, place.name, place.vicinity);
        		        
        		        iOverlay.addOVerlay(oItem);
        		        
        		        iOverlay.addOVerlay(oItem);
        	        	mapOverlays.add(iOverlay);
        	        	
        			}
        			
        			else if(s.contains("hospital"))
        			{
        				Drawable hMarker=this.getResources().getDrawable(R.drawable.hospital);
        		        
        		        iOverlay=new MyItemizedOverlay(hMarker,this);
        		        //GeoPoint to place on map
        		        gp=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
        		        
        		        //Map overlay item
        		        oItem=new OverlayItem(gp, place.name, place.vicinity);
        		        
        		        iOverlay.addOVerlay(oItem);
        		        
        		        iOverlay.addOVerlay(oItem);
        	        	mapOverlays.add(iOverlay);
        				
        			}
 
        			else if(s.contains("movie_theater"))
        			{
        				Drawable movieMarker=this.getResources().getDrawable(R.drawable.movierental);
        		        
        		        iOverlay=new MyItemizedOverlay(movieMarker,this);
        		        //GeoPoint to place on map
        		        gp=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
        		        
        		        //Map overlay item
        		        oItem=new OverlayItem(gp, place.name, place.vicinity);
        		        
        		        iOverlay.addOVerlay(oItem);
        		        
        		        iOverlay.addOVerlay(oItem);
        	        	mapOverlays.add(iOverlay);
        	        	
        			}
        			
        			else if(s.contains("pharmacy"))
        			{
        				Drawable pMarker=this.getResources().getDrawable(R.drawable.pharmacy);
        		        
        		        iOverlay=new MyItemizedOverlay(pMarker,this);
        		        //GeoPoint to place on map
        		        gp=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
        		        
        		        //Map overlay item
        		        oItem=new OverlayItem(gp, place.name, place.vicinity);
        		        
        		        iOverlay.addOVerlay(oItem);
        		        
        		        iOverlay.addOVerlay(oItem);
        	        	mapOverlays.add(iOverlay);
        	        	
        			}
        			else if(s.contains("hair_care"))
        			{
        				Drawable bMarker=this.getResources().getDrawable(R.drawable.barber);
        		        
        		        iOverlay=new MyItemizedOverlay(bMarker,this);
        		        //GeoPoint to place on map
        		        gp=new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
        		        
        		        //Map overlay item
        		        oItem=new OverlayItem(gp, place.name, place.vicinity);
        		        
        		        iOverlay.addOVerlay(oItem);
        		        
        		        iOverlay.addOVerlay(oItem);
        	        	mapOverlays.add(iOverlay);
        	        	
        			}
     
        		}
        		
        		
        	}
        	/*
        	iOverlay.addOVerlay(oItem);
        	mapOverlays.add(iOverlay);
        	*/
        }
		
        mapView.getController();
 
		mc.animateTo(gp);
		mc.setZoom(15);
		mc.setCenter(gp);
		
		mapView.invalidate();
	}



	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class MyItemizedOverlay extends ItemizedOverlay<OverlayItem>
	{
		private ArrayList<OverlayItem> mOverlays=new ArrayList<OverlayItem>();
		Context mContext;
		
		public MyItemizedOverlay(Drawable marker) {
			super(boundCenterBottom(marker));
		}
		
		public MyItemizedOverlay(Drawable marker,Context context) {
			super(boundCenterBottom(marker));
			mContext=context;
		}

		@Override
		protected OverlayItem createItem(int i) {
			return mOverlays.get(i);
		}

		@Override
		public int size() {
			return mOverlays.size();
		}
		
		public void addOVerlay(OverlayItem overlay)
		{
			mOverlays.add(overlay);
			populate();
		}
		
		
		@Override
		public boolean onTap(int i) {
			
			OverlayItem item= mOverlays.get(i);
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	        dialog.setTitle(item.getTitle());
	        dialog.setMessage(item.getSnippet());
	        dialog.show();
			
			return true;
		}

		
	}
}
