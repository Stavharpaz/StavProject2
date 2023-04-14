package com.example.myproj;

public class Player implements Collidable{

    private final int COLPLAYER;
    private final int ROWINDEX;
    private int colIndex;

    public Player(int playerCol, int playerLocation)
    {
        ROWINDEX =playerLocation-1;
        colIndex =1;
        COLPLAYER = playerCol;
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



}
