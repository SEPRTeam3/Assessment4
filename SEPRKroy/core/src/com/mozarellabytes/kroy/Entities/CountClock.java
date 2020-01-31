package com.mozarellabytes.kroy.Entities;

import java.awt.*;

public class CountClock {
    /*
    seconds and millisecond in the countdown Clock
     */
    private static int seconds, millisecond;
    private static int x,y,flag;

    /*
    Constructor
     */
    public CountClock() {
        seconds = 10;
        millisecond = 0;
        x = 750;
        y = 1030;
    }

    /*
    Get X Position of the counter clock
     */
    public static int getXPosition(){
        return x;
    }

    /*
   Get Y Position of the counter clock
    */
    public static int getYPosition(){
        return y;
    }


    /*
    Initialise time and clock position
     */
    static {
        seconds = 200;
        millisecond = 0;
        x = 750;
        y = 1030;
        flag = 0;
    }

    /*
    Set remaining time, with counting down, state 0 means counting and 1 means pause
    */
    public static String set_clock(int state){
        if(state == 0) {
            if (millisecond == 0) {
                millisecond = 99;
                seconds--;
            } else {
                millisecond--;
            }
        }

        /*
        Get the remaining time and return it
         */
        String remainTime = null;
        if ((seconds == 0 && millisecond == 0) || flag == 1){
            remainTime = "00:00";
            flag = 1;
        }

        else if(seconds < 100 && flag == 0 && millisecond < 10){
            remainTime = Integer.toString(seconds) + " : 0" + Integer.toString(millisecond);
        }

        else if(seconds < 100 && flag == 0){
            remainTime = Integer.toString(seconds) + " : " + Integer.toString(millisecond);
        }

        else if(seconds > 100 && flag == 0 && millisecond < 10){
            remainTime =Integer.toString(seconds) + " : 0" + Integer.toString(millisecond);
        }

        else{
            remainTime = Integer.toString(seconds) + " : " + Integer.toString(millisecond);
        }

        return remainTime;
    }
}


