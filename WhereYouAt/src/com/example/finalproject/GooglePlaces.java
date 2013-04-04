package com.example.finalproject;

import org.apache.http.client.HttpResponseException;

import android.util.Log;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;


public class GooglePlaces {

    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
 
    // Google API Key
    private static final String API_KEY = "AIzaSyBxM2IbnBKEa665zmy8hkjXfv8-JcShdpQ";
 
    // Google Places search url's
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
 
    private double _latitude;
    private double _longitude;
    private double _radius;
 
    //Search for places of type 'types' within the radius of lat and long
    public PlacesList search(double latitude, double longitude, double radius, String types)
            throws Exception {
 
        this._latitude = latitude;
        this._longitude = longitude;
        this._radius = radius;
 
        try {
 
            HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
            HttpRequest request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
            
            //Inserts values into query parameters to Google Places API
            request.getUrl().put("key", API_KEY);
            request.getUrl().put("location", _latitude + "," + _longitude);
            request.getUrl().put("radius", _radius);
            request.getUrl().put("sensor", "false");
            if(types != null)
                request.getUrl().put("types", types);
 
            //Allows us to parse JSON request in the manner listed in PlacesList.java
            //Basically converts the JSON received into a java data model that is easy to work with
            PlacesList list = request.execute().parseAs(PlacesList.class);
            
            // Check log cat for places response status
            //Should return Places OK if search is good
            Log.d("Places Status", "" + list.status);
         
            return list;
          
        } catch (HttpResponseException e) {
            Log.e("Error:", e.getMessage());
            return null;
        }
 
    }
 
 
    //Create HTTP Request Factory
    public static HttpRequestFactory createRequestFactory(
            final HttpTransport transport) {
        return transport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                GoogleHeaders headers = new GoogleHeaders();
                headers.setApplicationName("Friend Finder");
                request.setHeaders(headers);
                JsonObjectParser parser=new JsonObjectParser(new AndroidJsonFactory());
                request.setParser(parser);
            }
        });
    }
}
