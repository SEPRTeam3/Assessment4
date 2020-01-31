package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;

public class CountClock {
    /*
    seconds and millisecond in the countdown Clock
     */
    private int seconds, millisecond;
    private long startTime;
    private long pauseTime;
    private String remainTime;
    private static int x,y,flag;

    /*
    Constructor
     */
    public CountClock() {
        startTime = TimeUtils.millis();
        pauseTime = 0;
        seconds = 200;
        millisecond = 0;
        x = 750;
        y = 1030;
    }


    /*
    Initialise time and clock position
     */
    static {
        x = 750;
        y = 1030;
        flag = 0;
    }

    /*
    Set remaining time, with counting down, state 0 means counting and 1 means pause
    */
    public String set_clock(int state){
        if(state == 0) {
            if(pauseTime != 0){
                startTime += pauseTime - startTime;
                pauseTime = 0;
            }
            } else {
                pauseTime = TimeUtils.millis();
            }


        /*
        Get the remaining time and return it
         */
        remainTime = (seconds - TimeUtils.timeSinceMillis(startTime)/1000) +":"+ (TimeUtils.timeSinceMillis(startTime) - (TimeUtils.timeSinceMillis(startTime)/1000)*1000);
        return remainTime;
    }
}


