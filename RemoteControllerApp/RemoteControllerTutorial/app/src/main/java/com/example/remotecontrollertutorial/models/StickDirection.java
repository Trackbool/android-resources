package com.example.remotecontrollertutorial.models;

import android.graphics.Point;

public class StickDirection {
    public static final int STICK_NONE = 0;
    public static final int STICK_UP = 1;
    public static final int STICK_UPRIGHT = 2;
    public static final int STICK_RIGHT = 3;
    public static final int STICK_DOWNRIGHT = 4;
    public static final int STICK_DOWN = 5;
    public static final int STICK_DOWNLEFT = 6;
    public static final int STICK_LEFT = 7;
    public static final int STICK_UPLEFT = 8;

    public static Point getDirectionAxes(int direction){
        Point point = new Point();
        if (direction == StickDirection.STICK_UP) {
            point.x = 0;
            point.y = 1;
        } else if (direction == StickDirection.STICK_UPRIGHT) {
            point.x = 1;
            point.y = 1;
        } else if (direction == StickDirection.STICK_RIGHT) {
            point.x = 1;
            point.y = 0;
        } else if (direction == StickDirection.STICK_DOWNRIGHT) {
            point.x = 1;
            point.y = -1;
        } else if (direction == StickDirection.STICK_DOWN) {
            point.x = 0;
            point.y = -1;
        } else if (direction == StickDirection.STICK_DOWNLEFT) {
            point.x = -1;
            point.y = -1;
        } else if (direction == StickDirection.STICK_LEFT) {
            point.x = -1;
            point.y = 0;
        } else if (direction == StickDirection.STICK_UPLEFT) {
            point.x = -1;
            point.y = 1;
        } else{
            point.x = 0;
            point.y = 0;
        }
        return point;
    }
}
