package com.ubs.opsit.interviews;

import java.util.regex.Pattern;

public class FormattedTimeMatcher implements TimeMatcher{
    //regex will match time 00-24:00-59:00-59
    private static Pattern TIME_PATTERN = Pattern.compile(
        "([01][0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]");

    @Override
    public boolean matches(String timeInp) {
        return TIME_PATTERN.matcher(timeInp).matches();
    }

}