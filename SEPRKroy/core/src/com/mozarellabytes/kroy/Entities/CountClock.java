package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;

public class CountClock {
    /*
    seconds and millisecond in the countdown Clock
     */
    private float remainTime;

    /*
    Constructor
     */
    public CountClock() {
        remainTime = 5*60;
    }

    /*
    Set remaining time, with counting down, state 0 means counting and 1 means pause
    */
    public String set_clock(int state){
        if(state == 0) {
            remainTime -= Gdx.graphics.getDeltaTime();
        }

        return (int)remainTime/60+":"+(int)remainTime%60;
    }
}


