package vinhl.test_diaoc;

import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.DiaOcDistrictCrawler;

import java.util.Map;

public class ListDistrictTest {
    public static void main(String[] args) {
        DiaOcDistrictCrawler crawler = new DiaOcDistrictCrawler();
        Map<String, String> test = crawler.getDistrict(WebsiteConstant.DiaOc.urlDiaOcHomePage);
        for (Map.Entry<String, String> entry : test.entrySet()) {
            System.out.print("'" + entry.getKey() + "',");
            //System.out.println("----");
        }
    }
}
