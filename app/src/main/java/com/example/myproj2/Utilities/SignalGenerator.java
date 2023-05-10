package com.example.myproj2.Utilities;



import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.myproj2.R;

public class SignalGenerator {

    private static SignalGenerator instance = null;
    private Context context;
    private static Vibrator vibrator;

    private MediaPlayer mediaPlayer;

    public SignalGenerator(Context context) {
        this.context = context;
    }



    public static void init(Context context) {
        if (instance == null) {
            instance = new SignalGenerator(context);
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
    }

    public static SignalGenerator getInstance() {
        return instance;
    }

    public void toast(String text, int lengthShort) {

        Toast
                .makeText(context, text, text.length())
                .show();
    }

    public void vibrate(long length){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(length, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(length);
        }
    }




    public void crashSound(){
        mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.obs_col_sound);
        mediaPlayer.setVolume(1.0f,1.0f);
        mediaPlayer.start();
    }

    public void successSound(){
        mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.life_col_sound);
        mediaPlayer.setVolume(1.0f,1.0f);
        mediaPlayer.start();
    }






}
