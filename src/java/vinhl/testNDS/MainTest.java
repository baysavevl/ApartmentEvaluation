package vinhl.testNDS;

import vinhl.constant.AppConstant;
import vinhl.constant.Constants;
import vinhl.crawlers.NhaDatNhanhApartmentListCrawler;

import java.io.IOException;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) throws IOException {
        Map<String, String> test;

        String currentUrl = "https://nhadatso.com/nha-dat-ban-can-ho-chung-cu-tai-quan-9-ho-chi-minh/?p=3";
        int disId = 4;

        Thread crawlingList;
        //crawlingList = new Thread(new NhaDatNhanhApartmentListCrawler(currentUrl, disId));
        //crawlingList.start();

        System.out.println(Constants.ID_APARTMENT);
    }
}
