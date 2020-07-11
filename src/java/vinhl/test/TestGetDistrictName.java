package vinhl.test;

import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.DiaOcDistrictCrawler;
import vinhl.crawlers.NhaDatSoDistrictGenerator;

import java.util.Map;

public class TestGetDistrictName {
    public static void main(String[] args) {
        NhaDatSoDistrictGenerator nhaDatSoDistrictGenerator = new NhaDatSoDistrictGenerator();
        Map<String, String> test = nhaDatSoDistrictGenerator.getDistrict();
        for (Map.Entry<String, String> entry : test.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            //System.out.println("----");
        }
    }
}
