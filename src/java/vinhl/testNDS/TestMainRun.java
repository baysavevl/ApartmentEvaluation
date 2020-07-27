package vinhl.testNDS;

import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.DistrictCrawler;
import vinhl.thread.NhaDatSoThread;

public class TestMainRun {
    public static void main(String[] args) {
        String url = WebsiteConstant.District.homePage;
        DistrictCrawler.getDistrict(url);

        NhaDatSoThread thread = new NhaDatSoThread();
        thread.start();
    }
}
