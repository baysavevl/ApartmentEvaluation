package vinhl.testDistrict;

import vinhl.entities.ApartmentManager;
import vinhl.jaxb.Apartment;

import java.util.List;

public class TestLocation {
    public static void main(String[] args) throws Exception {
        ApartmentManager am = new ApartmentManager();

        //String str1 = "Công viên phần mềm Quang Trung, Quận 12";

        String str1 = "182/32 Lê Văn Sỹ Phường 10 Quận Phú Nhuận";
        String str2 = "4/7/19 Hậu Giang Phường 4 Quận Tân Bình";
        String str3 = "Chung cư Sky9 Phường Phú Hữu Quận 9";
        String test = "";
        List<Apartment> result = am.getShortestRoute(str2, test, test);
        for (Apartment ap: result) {
            System.out.println(ap.getName());
            System.out.println(ap.getScore());
            System.out.println(ap.getAddress());
            System.out.println();
        }
    }
}
