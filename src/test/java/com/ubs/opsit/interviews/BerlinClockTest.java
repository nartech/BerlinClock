package com.ubs.opsit.interviews;

import com.ubs.opsit.interviews.exception.InvalidTimeException;
import org.junit.Assert;
import org.junit.Test;

public class BerlinClockTest {
    BerlinClock bc = new BerlinClock();

    @Test
    public void testConvert() {
        String output1 = bc.convertTime("00:00:00");
        String expected1 = "\nY\nOOOO\nOOOO\nOOOOOOOOOOO\nOOOO";
        Assert.assertEquals(expected1, output1);

        String output2 = bc.convertTime("13:17:01");
        String expected2 = "\nO\nRROO\nRRRO\nYYROOOOOOOO\nYYOO";
        Assert.assertEquals(expected2, output2);

        String output3 = bc.convertTime("23:59:59");
        String expected3 = "\nO\nRRRR\nRRRO\nYYRYYRYYRYY\nYYYY";
        Assert.assertEquals(expected3, output3);

        String output4 = bc.convertTime("24:00:00");
        String expected4 = "\nY\nRRRR\nRRRR\nOOOOOOOOOOO\nOOOO";
        Assert.assertEquals(expected4, output4);

    }


    @Test(expected = InvalidTimeException.class)
    public void testInvalidInput() {
        bc.convertTime("25:10:00");
    }

    @Test
    public void testInvalidTime2() {
        try {
            bc.convertTime("25:10:00:asdf");
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().contains("Invalid 24 hour format time"));
        }

        try {
            bc.convertTime("25:10:00");
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().contains("Invalid 24 hour format time"));
        }

        try {
            bc.convertTime("15:99:00");
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().contains("Invalid 24 hour format time"));
        }
     try {
            bc.convertTime("15:39:70");
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().contains("Invalid 24 hour format time"));
        }
     try {
            bc.convertTime("24:39:00");
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().contains("Invalid time"));
        }
    }

}
