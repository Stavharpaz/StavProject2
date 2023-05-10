package com.example.myproj2.Utilities;


import static android.location.LocationManager.GPS_PROVIDER;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;


public class LocationOnMap implements LocationListener  {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private double longtitude;
    private double latitude;
    private LocationManager locationManager;

    @Override
    public void onLocationChanged(@NonNull Location location) {
    }

    public LocationOnMap(AppCompatActivity activity) {
        locationManager= (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION
            );
        }
        locationManager.requestLocationUpdates(GPS_PROVIDER,0,0,this);
    }


    public void updateLocation(AppCompatActivity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longtitude = location.getLongitude();

            }
        }
        stopLocationTrack();
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    private void stopLocationTrack() {
        locationManager.removeUpdates(this);
    }
}