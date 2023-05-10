package com.example.myproj2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myproj2.Fragment.ListFragment;

import com.example.myproj2.Fragment.MapFragment;
import com.example.myproj2.Interfaces.RecordCallback;
import com.example.myproj2.Models.Record;
import com.example.myproj2.Utilities.LocationOnMap;


public class GameOverActivity extends AppCompatActivity {


    private ListFragment listFragment;
    private MapFragment mapFragment;

    private LocationOnMap locationOnMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        initFragments();

        listFragment.getRecordAdapter().setRecordCallback(new RecordCallback() {
            @Override
            public void itemClicked(Record record) {

                mapFragment.zoomOnRecord(record.getLongitude(), record.getLatitude());
            }


        });
        beginTransactions();

    }

    private void initFragments(){
        Intent previousIntent = getIntent();
        String recordDistance = previousIntent.getStringExtra(Record.KEY_RECORD_DISTANCE);
        String recordName = previousIntent.getStringExtra(Record.KEY_RECORD_NAME);
        mapFragment = new MapFragment();
        locationOnMap = new LocationOnMap(this);
        locationOnMap.updateLocation(this);
        Record newRecord = new Record(recordDistance,recordName,locationOnMap.getLongtitude(), locationOnMap.getLatitude());
        listFragment = new ListFragment(newRecord);
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }



}

















