package vinhl.test;

import vinhl.crawlers.NhaDatSoDistrictGenerator;
import vinhl.crawlers.NhaDatSoPageCrawler;

import java.util.Map;

public class NDS_C_T2 {
    public static void main(String[] args) {
        Map<String, String> test;
        NhaDatSoPageCrawler nhaDatSoPageCrawler = new NhaDatSoPageCrawler();
        NhaDatSoDistrictGenerator nhaDatSoDistrictGenerator = new NhaDatSoDistrictGenerator();

        int numberPage = 0;
        test = nhaDatSoDistrictGenerator.getDistrict();
        for (Map.Entry<String, String> entry : test.entrySet()) {
            numberPage = nhaDatSoPageCrawler.getPages(entry.getValue());
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            System.out.println("Page = " + numberPage);
            System.out.println("----");
        }
    }
}
