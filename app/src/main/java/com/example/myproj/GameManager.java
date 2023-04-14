package com.example.myproj;


import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.ArrayList;

public class GameManager {


    private final int FINALLIFE = 3;
    
    private int collisions;

    private ArrayList<Obstacle> obsLst; //amount of obstacles

    private Player player; //playerLocation

    private int rowField;
    private int colField;

    public GameManager(int rowF, int colF) {
        this.collisions = 0;
        this.rowField = rowF;
        this.colField = colF;
        obsLst = new ArrayList<>();
        player = new Player(colF, rowF);

    }


    public int getObsLstLength() {
        return obsLst.size();
    }


    public Player getPlayer(){
        return player;
    }



    public Obstacle getObstacle(int index) {
        return obsLst.get(index);
    }


    public int getCollisions() {
        return collisions;
    }

    public void setCollisions() {
        this.collisions += 1;

    }

    public boolean isGameEnded() {
        return FINALLIFE < collisions;
    }

    public boolean checkCollision(int index, Vibrator v) {
        if (!obsLst.get(index).isMovable(1) && player.getCOLINDEX() == obsLst.get(index).getCOLINDEX())
        {
            setCollisions();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

            else {
                //deprecated in API 26
                v.vibrate(500);
            }
            return true;
        }
        return false;
    }

    public int chooseLane()
    {
        int lane =  (int) (Math.random() * 3);
        putObstacle(lane);
        return lane;
    }

    public void putObstacle(int lane){
        obsLst.add(new Obstacle(rowField, lane));

    }

    public void removeObs(int index) {
        obsLst.remove(index);
    }

}














