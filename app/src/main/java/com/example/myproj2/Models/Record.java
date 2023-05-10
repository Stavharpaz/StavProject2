package com.example.myproj2.Models;



public class Record {
    private String name;



   private int distance =0;
    private double longitude;
   private double latitude;

    public static final String KEY_RECORD_NAME = "KEY_RECORD_NAME";
    public static final String KEY_RECORD_DISTANCE = "KEY_RECORD_DISTANCE";




    public Record(String recordDistance, String recordName , double longitude, double latitude) {
        name = recordName;
        distance = Integer.parseInt(recordDistance);
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public String getName() {
        return name;
    }




    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getDistance() {
        return distance;
    }


}
