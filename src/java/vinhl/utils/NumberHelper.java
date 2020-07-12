package vinhl.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberHelper {
    public static int getNumberInString(String text, int defaultValue) {
        if (text != null && !text.isEmpty()) {
            String regex = "[0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String result = matcher.group();
                try {
                    int number = Integer.parseInt(result);
                    return number;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        return defaultValue;
    }
}
