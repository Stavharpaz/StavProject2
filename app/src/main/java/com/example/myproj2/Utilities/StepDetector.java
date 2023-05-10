package com.example.myproj2.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.myproj2.Interfaces.StepCallback;

public class StepDetector {
    private Sensor sensor;
    private SensorManager sensorManager;

    private StepCallback stepCallback;

    private int stepCounterX;
    private long timestamp = 0;

    private final int X_MAX_STEPS;



    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, StepCallback stepCallback, final int xMax, int xlocation) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        X_MAX_STEPS=xMax;
        stepCounterX = xlocation;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];

                calculateStep(x);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }





    public void calculateStep(float x) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x > 3.0 && 0< stepCounterX ) {
                stepCounterX--;
                stepCallback.stepX(-1);


            } else if (x < -3.0 &&  X_MAX_STEPS > stepCounterX) {
                stepCounterX++;
                stepCallback.stepX(1);
            }
        }
    }



    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

}
