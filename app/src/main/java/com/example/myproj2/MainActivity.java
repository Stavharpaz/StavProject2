package com.example.myproj2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Vibrator;
import android.util.Log;
import android.view.View;


import com.example.myproj2.Interfaces.StepCallback;
import com.example.myproj2.Models.Record;
import com.example.myproj2.Utilities.ImageLoader;
import com.example.myproj2.Utilities.StepDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final String KEYSPEED = "KEY_SPEED";
    public static final String KEYMODE = "KEY_MODE";

    public static final String KEYNAME = "KEY_NAME";
    private boolean mode;

    private AppCompatImageView background;

    private final int DELAY = 500;

    private int delaySpeed = 4;

    private int delaySpeed2 = 2;
    private FloatingActionButton buttonL;


    private StepDetector stepDetector;

    private FloatingActionButton buttonR;
    private ShapeableImageView[] main_IMG_hearts;

    private GameManager gameManager;

    private ShapeableImageView[][] gameField;


    private MaterialTextView distanceCounter;
    private Timer timer;
    private Intent previousIntent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        previousIntent = getIntent();
        gameManager = new GameManager(gameField.length, gameField[0].length);
        setSpeed();
        setMode();

        ImageLoader.getInstance().loadImage(R.drawable.road, background);


        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameManager.getPlayer().isMovable(-1))
                    setPlayerPos(-1);
            }
        });

        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameManager.getPlayer().isMovable(1)) // Check if car is not already at leftmost/rightmost lane
                    setPlayerPos(1);
            }
        });
        moveObstacle();



    }

    private void setMode() {

        mode = previousIntent.getBooleanExtra(KEYMODE,false);

        if(mode){
            buttonL.setActivated(false);
            buttonR.setActivated(false);
            buttonL.setVisibility(View.INVISIBLE);
            buttonR.setVisibility(View.INVISIBLE);
            initStepDetector();
            stepDetector.start();

        }
    }

    public void setPlayerPos(int move) {
        gameField[gameManager.getPlayer().getROWINDEX()][gameManager.getPlayer().getCOLINDEX()].setVisibility(View.INVISIBLE);
        gameManager.getPlayer().setIndex(move);
        gameField[gameManager.getPlayer().getROWINDEX()][gameManager.getPlayer().getCOLINDEX()].setVisibility(View.VISIBLE);

    }



    private void initStepDetector() {
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepX(int move) {
                Log.d("", "gameManager.getPlayer().getCOLINDEX() = "+gameManager.getPlayer().getCOLINDEX()+"\n");
                setPlayerPos(move);
            }



        }, gameManager.getPlayer().getCOLPLAYER()-1, gameManager.getPlayer().getCOLINDEX());
    }


    private void setSpeed() {
        boolean speed = previousIntent.getBooleanExtra(KEYSPEED, false);

        if(speed){
            delaySpeed=2;
            delaySpeed2=1;
        }
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
            }, delaySpeed*DELAY, delaySpeed2*DELAY);
        }
    }




    private void findViews() {

        background = findViewById(R.id.main_IMG_background);


        gameField = new ShapeableImageView[][]{
            {findViewById(R.id.obs1), findViewById(R.id.obs2),findViewById(R.id.obs16) ,findViewById(R.id.obs17),findViewById(R.id.obs3)},
            {findViewById(R.id.obs4), findViewById(R.id.obs5), findViewById(R.id.obs6), findViewById(R.id.obs18), findViewById(R.id.obs19)},
            {findViewById(R.id.obs7), findViewById(R.id.obs8), findViewById(R.id.obs9), findViewById(R.id.obs20),findViewById(R.id.obs21)},
            {findViewById(R.id.obs10), findViewById(R.id.obs11), findViewById(R.id.obs12), findViewById(R.id.obs22), findViewById(R.id.obs23)},
            {findViewById(R.id.obs13), findViewById(R.id.obs14), findViewById(R.id.obs15), findViewById(R.id.obs24), findViewById(R.id.obs25)},
            {findViewById(R.id.obs26), findViewById(R.id.obs27), findViewById(R.id.obs28), findViewById(R.id.obs29),findViewById(R.id.obs30)},
            {findViewById(R.id.obs31), findViewById(R.id.obs32), findViewById(R.id.obs33), findViewById(R.id.obs34), findViewById(R.id.obs35)},
            {findViewById(R.id.obs40), findViewById(R.id.obs39), findViewById(R.id.obs38), findViewById(R.id.obs37), findViewById(R.id.obs36)},
            {findViewById(R.id.main_car0), findViewById(R.id.main_car1), findViewById(R.id.main_car2),findViewById(R.id.main_car3), findViewById(R.id.main_car4)}};


        makeInvisible(gameField, gameField.length, gameField[0].length);

        buttonL = findViewById(R.id.Move_car_to_the_left);
        buttonR = findViewById(R.id.Move_car_to_the_right);



        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};

        distanceCounter = findViewById(R.id.DistanceCounter);


    }


    private void makeInvisible(ShapeableImageView [][]gameField, int row, int col)
    {
        for (int r = 0; r < row; r++)
            for(int c=0 ; c< col; c++)
                gameField[r][c].setVisibility(View.INVISIBLE);

        gameField[row-1][col/2].setVisibility(View.VISIBLE);
    }





    private void refreshUI() {

        int row,col, amouObs = gameManager.getObsLstLength();

        for (int i = 0; i < amouObs ; i++)
        {
            row = gameManager.getObstacle(i).getROWINDEX();
            col = gameManager.getObstacle(i).getCOLINDEX();

            gameField[row][col].setVisibility(View.INVISIBLE);

            if (!(gameManager.getObstacle(i).isMovable(1))) {
                gameManager.removeObs(i);
                i--;
                amouObs--;

            }

            else {
                row= gameManager.getObstacle(i).setIndex(1);
                gameField[row][col].setImageDrawable(gameField[row-1][col].getDrawable());
                gameField[row][col].setVisibility(View.VISIBLE);


                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if (gameManager.getObstacle(i).getResourceId() == R.drawable.rock && gameManager.checkCollision(1, i, getApplicationContext(), "CRUSH!", 1)) {    //rock collision
                    if (gameManager.isGameEnded()) {
                        if (timer != null)
                            timer.cancel();
                        timer = null;
                        gameIsFinished();
                    }

                    else
                        main_IMG_hearts[gameManager.getCollisions()-1].setVisibility(View.INVISIBLE);
                }
                 
                if(gameManager.getObstacle(i).getResourceId() == R.drawable.ic_heart && gameManager.isCollisionAlreadyExist() && gameManager.checkCollision(-1, i, getApplicationContext(), "WELL DONE!", 0))   //heart collision
                      main_IMG_hearts[gameManager.getCollisions()].setVisibility(View.VISIBLE);

            }


        }
        gameManager.getPlayer().setRecord();
        distanceCounter.setText("" + gameManager.getPlayer().getDistance());
        insertObstacle();
    }

    private void insertObstacle() {

              int wichLane=gameManager.chooseNumber(gameField[0].length);
              int resourceId = gameManager.chooseNumber(8)==0 ? R.drawable.ic_heart : R.drawable.rock;
              gameManager.putObstacle(wichLane, resourceId);

              Drawable drawable = ContextCompat.getDrawable(this, resourceId);
              gameField[0][wichLane].setImageDrawable(drawable);
              gameField[0][wichLane].setVisibility(View.VISIBLE);
    }


    private void gameIsFinished() {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra(Record.KEY_RECORD_DISTANCE,gameManager.getPlayer().getDistance()+"");
        intent.putExtra(Record.KEY_RECORD_NAME,previousIntent.getStringExtra(MainActivity.KEYNAME)+"");
        startActivity(intent);
        if(mode)
            stepDetector.stop();
        finish();
    }










}