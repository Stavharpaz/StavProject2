package com.example.myproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView main_IMG_background;

    private final int DELAY = 1500;
    private FloatingActionButton buttonL;

    private FloatingActionButton buttonR;
    private ShapeableImageView[] main_IMG_hearts;

    private GameManager gameManager;

    private ShapeableImageView[][] gameField;



    private Timer timer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        Glide
                .with(this)
                .load(R.drawable.road)
                .centerCrop()
                .into(main_IMG_background);

        gameManager = new GameManager(gameField.length, gameField[0].length);




        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameManager.getPlayer().isMovable(-1)) {
                    gameField[gameManager.getPlayer().getROWINDEX()][gameManager.getPlayer().getCOLINDEX()].setVisibility(View.INVISIBLE);
                    gameManager.getPlayer().setIndex(-1);
                    gameField[gameManager.getPlayer().getROWINDEX()][gameManager.getPlayer().getCOLINDEX()].setVisibility(View.VISIBLE);

                }
            }
        });

        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (gameManager.getPlayer().isMovable(1)) { // Check if car is not already at leftmost lane
                        gameField[gameManager.getPlayer().getROWINDEX()][gameManager.getPlayer().getCOLINDEX()].setVisibility(View.INVISIBLE);
                        gameManager.getPlayer().setIndex(1);
                        gameField[gameManager.getPlayer().getROWINDEX()][gameManager.getPlayer().getCOLINDEX()].setVisibility(View.VISIBLE);

                    }

            }
        });
        moveObstacle();



    }


    private void moveObstacle() {
        if (timer == null) {
            timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    runOnUiThread(() -> {
                        refreshUI();

                    });
                }
            }, 3*DELAY, DELAY);
        }
    }




    private void findViews() {

        main_IMG_background = findViewById(R.id.main_IMG_background);


        gameField = new ShapeableImageView[][]{
            {findViewById(R.id.rock1), findViewById(R.id.rock2), findViewById(R.id.rock3)},
            {findViewById(R.id.rock4), findViewById(R.id.rock5), findViewById(R.id.rock6)},
            {findViewById(R.id.rock7), findViewById(R.id.rock8), findViewById(R.id.rock9)},
            {findViewById(R.id.rock10), findViewById(R.id.rock11), findViewById(R.id.rock12)},
            {findViewById(R.id.rock13), findViewById(R.id.rock14), findViewById(R.id.rock15)},
            {findViewById(R.id.main_car0), findViewById(R.id.main_car1), findViewById(R.id.main_car2)}};


        makeInvisible(gameField, gameField.length, gameField[0].length);
        buttonL = findViewById(R.id.Move_car_to_the_left);
        buttonR = findViewById(R.id.Move_car_to_the_right);



        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};


    }


    private void makeInvisible(ShapeableImageView [][]gameField, int row, int col)
    {
        for (int r = 0; r < row; r++)
            for(int c=0 ; c< col; c++)
                gameField[r][c].setVisibility(View.INVISIBLE);

        gameField[row-1][col-2].setVisibility(View.VISIBLE);
    }





    private void refreshUI() {

        int row,col, amouObs = gameManager.getObsLstLength();

        for (int i = 0; i < amouObs ; i++)
        {
            row = gameManager.getObstacle(i).getROWINDEX();
            col = gameManager.getObstacle(i).getCOLINDEX();


            if (!(gameManager.getObstacle(i).isMovable(1))) {
                gameField[row][col].setVisibility(View.INVISIBLE);
                gameManager.removeObs(i);
                i--;
                amouObs--;

            }

            else {
                gameField[row][col].setVisibility(View.INVISIBLE);
                row= gameManager.getObstacle(i).setIndex(1);
                gameField[row][col].setVisibility(View.VISIBLE);


                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (gameManager.checkCollision(i, v)) {
                    // Vibrate for 500 milliseconds

                    if (gameManager.isGameEnded()) {
                        if (timer != null)
                            timer.cancel();
                        timer = null;
                        gameIsFinished();
                    }


                    else {
                        main_IMG_hearts[gameManager.getCollisions()-1].setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), " CRASH!", Toast.LENGTH_LONG).show();
                    }

                }
            }


        }
        int wichLane=gameManager.chooseLane();
        gameField[0][wichLane].setVisibility(View.VISIBLE);

    }



    private void gameIsFinished() {
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);

        finish();
    }

}