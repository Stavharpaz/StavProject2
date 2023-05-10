package com.example.myproj2;

import android.app.Application;

import com.example.myproj2.Utilities.ImageLoader;
import com.example.myproj2.Utilities.MySP3;
import com.example.myproj2.Utilities.SignalGenerator;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.initImageLoader(this);
        MySP3.init(this);
        SignalGenerator.init(this);

    }
}
