package com.example.finalproject;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Key;

public class Place implements Serializable {
	
	@Key
	public String id;
	
	@Key
	public String name;
	
	@Key
	public String reference;

    @Key
    public String icon;
 
    @Key
    public String vicinity;
 
    @Key
    public Geometry geometry;
 
    @Key
    public String formatted_address;
 
    @Key
    public String formatted_phone_number;
    
    @Key
    public String[] types;
	
	public String toString()
	{
		return name+" - "+ id +" - "+ reference;
	}
	
	public static class Geometry implements Serializable
	{
		@Key
		public Location location;
	}
	
	public static class Location implements Serializable
	{
		@Key
		public double lat;
		
		@Key 
		public double lng;
		
		public String toString()
		{
			return Double.toString(lat)+","+Double.toString(lng);
		}
	}
}
