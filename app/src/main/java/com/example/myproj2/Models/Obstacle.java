package com.example.myproj2.Models;

import com.example.myproj2.Interfaces.Collidable;

public class Obstacle implements Collidable {

    private final int ROWOBSTACLE;
    private int rowIndex;
    private final int COLINDEX;

    private int resourceId;


    public Obstacle(int obstacleRows, int location, int resourceId)
    {
        rowIndex =0;
        COLINDEX =location;
        ROWOBSTACLE = obstacleRows-1;
        this.resourceId =resourceId;
    }


    @Override
    public int getROWINDEX() {
        return rowIndex;
    }

    @Override
    public int getCOLINDEX() {
        return COLINDEX;
    }

    public int getResourceId() {
        return resourceId;
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
