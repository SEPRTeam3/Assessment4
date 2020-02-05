package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;

public class CountClock {
    /*
    seconds and millisecond in the countdown Clock
     */
    private float remainTime;

    /*
    Constructor
     */
    public CountClock() {
        remainTime = 900;
    }

    /*
    Set remaining time, with counting down, state 0 means counting and 1 means pause
    */
    public String set_clock(int state) {

        if (state == 0) {
            remainTime -= Gdx.graphics.getDeltaTime();
        }

        if(remainTime / 60 >= 10 && remainTime % 60 < 10 && remainTime >= 0){
            return (int) remainTime / 60 + " : 0" + (int) remainTime % 60;
        }

        else if(remainTime / 60 >= 10 && remainTime % 60 >= 10 && remainTime >= 0){
            return (int) remainTime / 60 + " : " + (int) remainTime % 60;
        }

        else if(remainTime / 60 <= 10 && remainTime % 60 >=10 && remainTime >= 0){
            return "0" + (int) remainTime / 60 + " : " + (int) remainTime % 60;
        }

        else if(remainTime / 60 <= 10 && remainTime % 60 <10 && remainTime >= 0){
            return "0" + (int) remainTime / 60 + " : 0" + (int) remainTime % 60;
        }

        else if(remainTime < 0){
            return "00 : 00";
        }

        else {
            return "0" + (int) remainTime / 60 + " : " + (int) remainTime % 60;
        }
    }
}


