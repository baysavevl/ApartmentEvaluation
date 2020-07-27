package vinhl.testNDS;

import vinhl.crawlers.BaseCrawler;
import vinhl.crawlers.NhaDatSoApartmentListCrawler;

public class NDS_C_T3 extends BaseCrawler {

    public static void main(String[] args) {
        String url = "https://nhadatso.com/nha-dat-ban-can-ho-chung-cu-tai-quan-9-ho-chi-minh/?p=3";

        NhaDatSoApartmentListCrawler crawler = new NhaDatSoApartmentListCrawler(url, 12);
        crawler.run();

        //String res = crawler.getListApartment(url);
        //System.out.println(res);

    }
}
