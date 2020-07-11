package vinhl.utils;

public class RefineHelper {
    public static String removeNeedlessPrefixDistrict(String str) {
        if (!str.matches(".*\\d.*")) {
            str = str.replace("Quận ", "");
            str = str.replace("Huyện ", "");
        }
        return str;
    }
}
