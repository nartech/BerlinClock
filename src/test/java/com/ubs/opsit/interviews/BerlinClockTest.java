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


    @Test
    public void testSeconds() {
        Assert.assertEquals("Y", bc.getSeconds(20));
        Assert.assertEquals("O", bc.getSeconds(33));
    }

    @Test
    public void testTopHours() {
        Assert.assertEquals("OOOO", bc.getTopHours(3));
        Assert.assertEquals("ROOO", bc.getTopHours(6));
        Assert.assertEquals("RROO", bc.getTopHours(12));
        Assert.assertEquals("RRRO", bc.getTopHours(16));
        Assert.assertEquals("RRRR", bc.getTopHours(21));

        Assert.assertEquals(4, bc.getTopHours(3).length());
        Assert.assertEquals(4, bc.getTopHours(12).length());
    }

    @Test
    public void testBottomHours() {
        Assert.assertEquals("OOOO", bc.getBottomHours(20));
        Assert.assertEquals("ROOO", bc.getBottomHours(1));
        Assert.assertEquals("RROO", bc.getBottomHours(12));
        Assert.assertEquals("RRRO", bc.getBottomHours(18));
        Assert.assertEquals("RRRR", bc.getBottomHours(24));

        Assert.assertEquals(4, bc.getBottomHours(17).length());
        Assert.assertEquals(4, bc.getBottomHours(22).length());
    }

    @Test
    public void testTopMinutes() {
        Assert.assertEquals("YYRYOOOOOOO", bc.getTopMinutes(20));
        Assert.assertEquals("OOOOOOOOOOO", bc.getTopMinutes(1));
        Assert.assertEquals("YYRYYROOOOO", bc.getTopMinutes(32));
        Assert.assertEquals("YYROOOOOOOO", bc.getTopMinutes(18));
        Assert.assertEquals("YYRYYRYYRYY", bc.getTopMinutes(59));

        Assert.assertEquals(11, bc.getTopMinutes(17).length());
        Assert.assertEquals(11, bc.getTopMinutes(22).length());
    }

    @Test
    public void testBottomMinutes() {
        Assert.assertEquals("OOOO", bc.getBottomMinutes(20));
        Assert.assertEquals("YOOO", bc.getBottomMinutes(1));
        Assert.assertEquals("YYOO", bc.getBottomMinutes(12));
        Assert.assertEquals("YYYO", bc.getBottomMinutes(18));
        Assert.assertEquals("YYYY", bc.getBottomMinutes(24));

        Assert.assertEquals(4, bc.getBottomMinutes(17).length());
        Assert.assertEquals(4, bc.getBottomMinutes(22).length());
    }


}
