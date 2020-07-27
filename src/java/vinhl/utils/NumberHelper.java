package vinhl.utils;

import vinhl.constant.Constants;

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
                    return Integer.parseInt(result);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return defaultValue;
                }
            }
        }

        return defaultValue;
    }

    public static double decodeMoney(String prefix, String suffix) {
        double number = Double.parseDouble(prefix.replace(",", "."));

        if (suffix.equalsIgnoreCase(Constants.MONEY_MILLION)) {
            return number * Constants.MILLION;
        } else if (suffix.equalsIgnoreCase(Constants.MONEY_BILLION)) {
            return number * Constants.BILLION;
        }
        return number;
    }

    public static int extractNumber(String str) {
        str = str.trim().replaceAll("\\D+", "");
        return Integer.parseInt(str);
    }

    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean containsNumber(String string)
    {
        return string.matches(".*\\d+.*")
                || string.matches(",*\\d+.*");
    }

}
