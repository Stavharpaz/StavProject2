package com.example.myproj2.Models;

import com.example.myproj2.Interfaces.Collidable;

public class Player implements Collidable {

    private final int COLPLAYER;
    private final int ROWINDEX;
    private int colIndex;
    private int distance=0;





    public Player(int playerCol, int playerLocation)
    {
        ROWINDEX =playerLocation-1;
        colIndex =playerCol/2;
        COLPLAYER = playerCol;
    }

    public void setRecord() {
        this.distance +=1;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public int getROWINDEX() {
        return ROWINDEX;
    }

    @Override
    public int getCOLINDEX() {
        return colIndex;
    }

    @Override
    public int setIndex(int num) {
        colIndex +=num;
        return colIndex;
    }

    @Override
    public boolean isMovable(int num) {
            return colIndex+num >=0 && colIndex+num < COLPLAYER;
    }


    public int getCOLPLAYER() {
        return COLPLAYER;
    }
}
