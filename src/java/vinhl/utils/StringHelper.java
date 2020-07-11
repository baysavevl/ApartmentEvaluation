package vinhl.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringHelper {
    public static String decodeUTF8(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);

        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        str = pattern.matcher(str).replaceAll("");
        str = str.replace("đ", "d");
        str = str.replace("Đ", "D");
        return str;
    }
}
