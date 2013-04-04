/************************************************************************/
/*																		*/
/*		MainActivity.java												*/
/*		Implemented by Kevin Sounthavong								*/
/*		Edited by Calvin C Chiu, Kevin Sounthavong						*/
/*																		*/
/*																		*/
/************************************************************************/

package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	 
	protected TextView textName;
	protected Button btnBump;
	protected EditText editName;
	protected double latitude, longitude;
	protected ConnectivityManager connMgr;
	protected ProgressDialog _busyGPS;
    protected FindLocation fl;
	
	//GooglePlaces instance
	protected GooglePlaces googlePlaces;
	
	//Places List
	protected PlacesList nearPlaces;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        //Get instance of connectivity manager
        connMgr=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo networkInfo=connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConnected=networkInfo.isConnected();
        
        //Check if Wifi is on
        //If Not, have an Alert Dialog pop up telling you to enable
        //Will send you to Settings when you click OK
        if(isWifiConnected==false)
        {
        	AlertDialog.Builder aDialogBuilder=new AlertDialog.Builder(this);
        	
        	//Set Title
        	aDialogBuilder.setTitle("No WiFi or Mobile Connection");
        	
        	//Set dialog message
        	aDialogBuilder.setMessage("Please enable WiFi").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);
					dialog.dismiss();
					
				}
			});
        	
        	//Create alert dialog
        	AlertDialog aDialog=aDialogBuilder.create();
        	
        	//Display dialog
        	aDialog.show();
        }
        
     
       textName=(TextView) findViewById(R.id.textName);
       btnBump=(Button) findViewById(R.id.btnBump);
       editName=(EditText) findViewById(R.id.editName);      

      
    		   
        //Instance of FindLocation
        //Will call GetLocation to get your current location
       fl=new FindLocation(MainActivity.this);       
       //uses GooglePlaces search function to look for nearby places of interest around your current location
       new LoadPlaces().execute();
    		   
    		// new on click
		btnBump.setOnClickListener(new View.OnClickListener(){
		    	
    	public void onClick(View v){
    		String temp = editName.getText().toString();
    		if(temp.trim().length() > 0){
    			Intent userInfo=new Intent(MainActivity.this,BumpTest.class);        		
    			//Send data to BumpTest Activity
    			userInfo.putExtra("LAT", fl.getLat());
    			userInfo.putExtra("LONG", fl.getLong());
    			userInfo.putExtra("NAME",temp);
    			userInfo.putExtra("near_places", nearPlaces);
    			startActivity(userInfo);
    		}
    		else
    			Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
    		
    	}
	   });

        
    }
    public void showBusy(){
    	_busyGPS = ProgressDialog.show(this,"Loading","Retrieving GPS Coords",true);
    }
    
    public void dismissBusy(){
    	_busyGPS.dismiss();
    }
    
    //AsycnTask to load Google Places
    public class LoadPlaces extends AsyncTask<String,Void,String>
    {
		//Retrieve Places API JSON here
    	
		protected String doInBackground(String... args) {
			
			//Instantiate google places object
			googlePlaces=new GooglePlaces();
			
			try{
				//Types of nearby places to look for
				String types="cafe|restaurant|bar|convenience_store|clothing_store|liquor_store|hospital|department_store|pharmacy|hair_care";
				
				double radius=1609; //1609 meters = 1 mile
				
				//Populate nearPlaces with nearby Places objects
				nearPlaces=googlePlaces.search(fl.getLat(), fl.getLong(), radius, types);
			}catch (Exception e){
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showBusy();
		}
		@Override
		protected void onPostExecute(String result){
			if(nearPlaces!=null && fl.location != null)
				dismissBusy();
		}
		
    }

}
