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
    private long usedTime;
    private String remainTime;
    private static int x,y,flag;
    private long last_pause_time = 0, last_start_time = 0;

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
    public String set_clock(int state) {

        if (state == 0) {    //playing
            pauseTime = TimeUtils.millis();
            last_start_time = startTime;
            if (pauseTime != 0) { //pause has been pressed
                last_pause_time = pauseTime;
                System.out.print(last_pause_time);
                last_pause_time = pauseTime - startTime;
                System.out.print(99999999);
                startTime += pauseTime - startTime; //pauseTime - startTime -> How long has been played
            }
        }

        if(state == 1) {        // pause
            System.out.print("aaaaaaaaaaaaaa");
            startTime = last_pause_time - last_start_time;
        }

        /*
        Get the remaining time and return it
         */
        remainTime = (seconds - TimeUtils.timeSinceMillis(startTime)/1000) +":"+ (TimeUtils.timeSinceMillis(startTime) - (TimeUtils.timeSinceMillis(startTime)/1000)*1000);
        return remainTime;
    }
}


