package com.ubs.opsit.interviews;

import java.lang.StringBuilder;
import com.ubs.opsit.interviews.exception.InvalidTimeException;

public class BerlinClock implements TimeConverter {


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
    private String getSeconds(int secs) {
        String ret = "O";
        if (secs % 2 == 0) {
            ret = "Y";
        }
        return "\n"+ret;
    }

    /**
     * This method returns hours rows (Second and Third)
     *      Second row shows hours in sum of 5 (hours/5 show that many Rs and remaining O)
     *      Third row shows hours in sum of 1 (hours%5 show that many Rs and remaining O)
     * */
    private String getHours(int hours) {
        int firstRowHours = hours / 5;
        int onLights=0;
        StringBuilder hoursLights = new StringBuilder("\n");
        //process first row
        for (onLights=0; onLights < firstRowHours; onLights++) {
            hoursLights.append("R");
        }

        for (int offLights = onLights; offLights < 4; offLights++) {
            hoursLights.append("O");
        }

        hoursLights.append("\n");
        //process second row
        int secondRowHours = hours % 5;

        for (onLights = 0; onLights < secondRowHours; onLights++) {
          hoursLights.append("R");
        }
        for (int offLights = onLights; offLights < 4; offLights++) {
          hoursLights.append("O");
        }

        return hoursLights.toString();
    }

    /**
     *  This method returns minutes rows (fourth and fifth)
     *      Fourth row shows minutes in sum of 5 (mins/5 and if index is 3,6,9 then R else Y and remaining O)
     *      Fifth row shows minutes in sum of 1 (mins%5 show that many Y and remaining O)
     * */
    private String getMinutes(int mins) {
        StringBuilder minuteLights = new StringBuilder("\n");

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

        minuteLights.append("\n");
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

        System.out.println("Begin convertTime " + aTime);

        if (aTime == null || aTime.isEmpty()){
            throw new InvalidTimeException("Empty time");
        }
        // check the format regex hh:mm:ss
        TimeMatcher matcher = new FormattedTimeMatcher();
        if (!matcher.matches(aTime)) {
            throw new InvalidTimeException("Invalid 24 hour format time");
        }

        String[] timeSplit = aTime.split(":");

        String secs = timeSplit[2];
        String hours = timeSplit[0];
        String minutes = timeSplit[1];

        //regex cannot handle case where hours = 24 hours but mins and seconds > 00
        String collapsedTime = hours+minutes+secs;
        if (Integer.parseInt(collapsedTime)>240000) {
            throw new InvalidTimeException("Invalid time");
        }

        return getSeconds(Integer.parseInt(secs)) +
                getHours(Integer.parseInt(hours)) +
                getMinutes(Integer.parseInt(minutes));
    }

    //JUnit test to verify the computed time is added to tests directory
    public static void main(String[] args) {
        BerlinClock bc = new BerlinClock();
        //bc.convertTime("00:00:00");
        //bc.convertTime("13:17:01");
//        bc.convertTime("23:59:59");
        System.out.println(bc.convertTime("24:00:00"));
    }
}
