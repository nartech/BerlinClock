package com.ubs.opsit.interviews;

import java.lang.StringBuilder;
import com.ubs.opsit.interviews.exception.InvalidTimeException;

public class BerlinClock implements TimeConverter {
    public static final String LINE_SEPARATOR = "\n";

    /**
     *   when the time is input like hh:mm:ss
     *
     *   First row shows seconds lights:  Y = when seconds is even else O
     *   Second row shows hours in sum of 5 (hours/5 show that many Rs and remaining O)
     *   Third row shows hours in sum of 1 (hours%5 show that many Rs and remaining O)
     *   Fourth row shows minutes in sum of 5 (mins/5 show that many if index is 3,6,9 then R else Y and remaining O)
     *   Fifth row shows minutes in sum of 1 (mins%5 show that many Y and remaining O)
     *
     * */

    /**
     * This method returns First row
     *          Y on even seconds
     *          O on odd seconds
     *  */
    public String getSeconds(int secs) {
        String ret = "O";
        if (secs % 2 == 0) {
            ret = "Y";
        }
        return ret;
    }

    /**
     * This method returns hours top row
     *      Second row shows hours in sum of 5 (hours/5 show that many Rs and remaining O)
     * */
    public String getTopHours(int hours) {
        int topRowHours = hours / 5;
        int onLights=0;
        StringBuilder hoursLights = new StringBuilder();
        for (onLights=0; onLights < topRowHours; onLights++) {
            hoursLights.append("R");
        }
        for (int offLights = onLights; offLights < 4; offLights++) {
            hoursLights.append("O");
        }

        return hoursLights.toString();
    }

    /**
     * This method returns hours bottom rows
     *      Third row shows hours in sum of 1 (hours%5 show that many Rs and remaining O)
     * */
    public String getBottomHours(int hours) {
        int onLights=0;
        StringBuilder hoursLights = new StringBuilder();
        int bottomRowHours = hours % 5;

        for (onLights = 0; onLights < bottomRowHours; onLights++) {
          hoursLights.append("R");
        }
        for (int offLights = onLights; offLights < 4; offLights++) {
          hoursLights.append("O");
        }

        return hoursLights.toString();
    }

    /**
     *  This method returns minutes top row
     *      Fourth row shows minutes in sum of 5 (mins/5 and if index is 3,6,9 then R else Y and remaining O)
     * */
    public String getTopMinutes(int mins) {
        StringBuilder minuteLights = new StringBuilder();

        int firstRowMins = mins / 5;
        int onLights=0;
        for (onLights=0; onLights < firstRowMins; onLights++) {
            if ((onLights+1)%3 == 0) {
                minuteLights.append("R");
            } else {
                minuteLights.append("Y");
            }
        }
        for (int offLights = onLights; offLights < 11; offLights++) {
            minuteLights.append("O");
        }

        return minuteLights.toString();
    }
    /**
     *  This method returns minutes bottom row
     *      Fifth row shows minutes in sum of 1 (mins%5 show that many Y and remaining O)
     * */
    public String getBottomMinutes(int mins) {
        StringBuilder minuteLights = new StringBuilder();

        int onLights=0;
        // process last row
        int secondRowMins = mins % 5;

        for (onLights = 0; onLights < secondRowMins; onLights++) {
            minuteLights.append("Y");
        }
        for (int offLights = onLights; offLights < 4; offLights++) {
            minuteLights.append("O");
        }

        return minuteLights.toString();
    }

    public String convertTime(String aTime){

       // System.out.println("Begin convertTime " + aTime);

        validateTime(aTime);

        String[] timeSplit = aTime.split(":");

        String secs = timeSplit[2];
        String hours = timeSplit[0];
        String minutes = timeSplit[1];

        //regex cannot handle case where hours = 24 hours but mins and seconds > 00
        //As the test expects 24:00:00 to be a valid time, handling it here
        //otherwise its not needed and regex can be modified
        String collapsedTime = hours+minutes+secs;
        if (Integer.parseInt(collapsedTime)>240000) {
            throw new InvalidTimeException("Invalid time");
        }

        return  LINE_SEPARATOR + getSeconds(Integer.parseInt(secs)) +
                LINE_SEPARATOR + getTopHours(Integer.parseInt(hours)) +
                LINE_SEPARATOR + getBottomHours(Integer.parseInt(hours)) +
                LINE_SEPARATOR + getTopMinutes(Integer.parseInt(minutes)) +
                LINE_SEPARATOR + getBottomMinutes(Integer.parseInt(minutes));
    }

    private void validateTime(String aTime) {
        if (aTime == null || aTime.isEmpty()){
            throw new InvalidTimeException("Empty time");
        }
        // check the format regex hh:mm:ss
        TimeMatcher matcher = new FormattedTimeMatcher();
        if (!matcher.matches(aTime)) {
            throw new InvalidTimeException("Invalid 24 hour format time");
        }
    }

    //JUnit test to verify the computed time is added to tests directory
//    public static void main(String[] args) {
//        BerlinClock bc = new BerlinClock();
//        //bc.convertTime("00:00:00");
//        //bc.convertTime("13:17:01");
////        bc.convertTime("23:59:59");
//        System.out.println(bc.convertTime("24:00:00"));
//    }
}
