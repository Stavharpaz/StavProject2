package com.example.myproj2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import com.example.myproj2.Utilities.ImageLoader;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


public class LoginScreenActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton startGameButton;

    private AppCompatEditText list_ET_name;

    private ToggleButton speedToggleButton;
    private ToggleButton modeToggleButton;

    private boolean speedChoise=false;

    private  boolean modeChoice = false;
    private AppCompatImageView background;



    private void clicked() {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.KEYSPEED, speedChoise);
        intent.putExtra(MainActivity.KEYMODE, modeChoice);
        if(list_ET_name.getText()!=null)
            intent.putExtra(MainActivity.KEYNAME, list_ET_name.getText().toString());
        else
            intent.putExtra(MainActivity.KEYNAME, "");

        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        ImageLoader.getInstance().loadImage(R.drawable.road, background);



        speedToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            speedChoise = isChecked;
        });

        modeToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            modeChoice = isChecked;
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked();
            }
        });


    }


    private void findViews() {
        startGameButton = findViewById(R.id.startGame);
        speedToggleButton = findViewById(R.id.SpeedButton);
        modeToggleButton = findViewById(R.id.SensorButton);
        background = findViewById(R.id.login_IMG_background);
        list_ET_name = findViewById(R.id.list_ET_name);
    }



}
