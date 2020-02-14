package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;

public class CountClock {
    /**
      * Time remaining in the countdown clock
      */
    private static float timeRemaining;
    private static float totalTime;

    /*
    Constructor for the CountClock class, instantiating the time remaining to 900 seconds.
     */
    public CountClock() {
        totalTime = 10;
        timeRemaining = totalTime;
    }

    public static void set_remain_Time(float set_time){
        timeRemaining = set_time;
    }

    public static float getTotalTime(){
        return totalTime;
    }

    
    /*
    Set remaining time, with counting down, state 0 means counting and 1 means pause
    */
    public String set_clock(int state) {

        if (state == 0) {
            timeRemaining -= Gdx.graphics.getDeltaTime();
        }

        if(timeRemaining / 60 >= 10 && timeRemaining % 60 < 10 && timeRemaining >= 0){
            return (int) timeRemaining / 60 + " : 0" + (int) timeRemaining % 60;
        }

        else if(timeRemaining / 60 >= 10 && timeRemaining % 60 >= 10 && timeRemaining >= 0){
            return (int) timeRemaining / 60 + " : " + (int) timeRemaining % 60;
        }

        else if(timeRemaining / 60 <= 10 && timeRemaining % 60 >=10 && timeRemaining >= 0){
            return "0" + (int) timeRemaining / 60 + " : " + (int) timeRemaining % 60;
        }

        else if(timeRemaining / 60 <= 10 && timeRemaining % 60 <10 && timeRemaining >= 0){
            return "0" + (int) timeRemaining / 60 + " : 0" + (int) timeRemaining % 60;
        }

        else if(timeRemaining < 0){
            return "00 : 00";
        }

        else {
            return "0" + (int) timeRemaining / 60 + " : " + (int) timeRemaining % 60;
        }
    }

    /**
     * @return true if the countdown timer has ended, else false.
     */
    public static boolean hasEnded() {
        if (timeRemaining < 0) {
            return true;
        }
        return false;
    }

    public static float getRemainTime() {
        return timeRemaining;
    }
}


