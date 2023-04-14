package com.example.myproj;

public class Obstacle implements Collidable{

    private final int ROWOBSTACLE;
    private int rowIndex;
    private final int COLINDEX;

    public Obstacle(int obstacleRows, int location)
    {
        rowIndex =0;
        COLINDEX =location;
        ROWOBSTACLE = obstacleRows-1;

    }


    @Override
    public int getROWINDEX() {
        return rowIndex;
    }

    @Override
    public int getCOLINDEX() {
        return COLINDEX;
    }

    @Override
    public int setIndex(int num) {
        rowIndex +=num;
        return rowIndex;
    }

    @Override
    public boolean isMovable(int num) {
            return rowIndex+num < ROWOBSTACLE;
    }

}
