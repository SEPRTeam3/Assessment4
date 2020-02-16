package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;

//#Assessment 3

/**
 * Class that handles the clock display at the top of the screen
 */
public class CountClock {
    /**
      * Time remaining in the countdown clock
      */
    private static float timeRemaining;

    /**
     * Total time for the count clock
     */
    private static float totalTime;

    /**
    Constructor for the CountClock class, instantiating the time remaining to 900 seconds.
     */
    public CountClock() {
        totalTime = 900;
        timeRemaining = totalTime;
    }

    /**
     * Direct set the remain time
     * @param set_time the time to set the clock to
     */
    public static void set_remain_Time(float set_time){
        timeRemaining = set_time;
    }

    /**
     * Get the total time of the count clock
     * @return the current time of the clock
     */
    public static float getTotalTime(){
        return totalTime;
    }

    /**
     * Calculates (and formats) the remaining time on the clock.
     * @param state 0 = counting down, 1 = paused
     * @return the clock's formatted time
     */
    public String set_clock(int state) {
        //Code that minuses time since last frame from time remaining when unpaused
        if (state == 0) {
            timeRemaining -= Gdx.graphics.getDeltaTime();
        }

        //Tree of if else statements that will convert time remaining in seconds to a string of the desired format
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

    /**
     * Get the remaining time of the count clock
     * @return
     */
    public static float getRemainTime() {
        return timeRemaining;
    }
}


