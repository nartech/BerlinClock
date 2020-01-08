package com.ubs.opsit.interviews;

import java.util.regex.Pattern;

public class FormattedTimeMatcher implements TimeMatcher{
    private static Pattern TIME_PATTERN = Pattern.compile(
            "^\\d{2}:\\d{2}:\\d{2}$");

    @Override
    public boolean matches(String timeInp) {
        return TIME_PATTERN.matcher(timeInp).matches();
    }

}