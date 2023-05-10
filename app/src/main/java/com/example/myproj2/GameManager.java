package com.example.myproj2;


import android.content.Context;
import android.widget.Toast;
import com.example.myproj2.Utilities.SignalGenerator;
import com.example.myproj2.Models.Obstacle;
import com.example.myproj2.Models.Player;

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

    public void setCollisions(int setNum) {
            this.collisions += setNum;
    }

    public boolean isCollisionAlreadyExist(){
       return collisions==0 ? false:true;
    }

    public boolean isGameEnded() {
        return FINALLIFE <= collisions;
    }

    public boolean checkCollision(int setNum, int index, Context context, String msg, int doVib) { //0=heart, 1=rock
        if (!obsLst.get(index).isMovable(1) && player.getCOLINDEX() == obsLst.get(index).getCOLINDEX())
        {
            setCollisions(setNum);

            SignalGenerator signalGenerator = new SignalGenerator(context);
            signalGenerator.init(context);
            signalGenerator.toast(msg, Toast.LENGTH_SHORT);

            if(doVib==1) {
                signalGenerator.vibrate(500);
                signalGenerator.crashSound();
            }
            else
                signalGenerator.successSound();

            return true;
        }
        return false;
    }

    public int chooseNumber(int limit)
    {
        return (int) (Math.random() * limit);
    }

    public void putObstacle(int lane, int resourceId){
        obsLst.add(new Obstacle(rowField, lane, resourceId));

    }

    public void removeObs(int index) {
        obsLst.remove(index);
    }


}














