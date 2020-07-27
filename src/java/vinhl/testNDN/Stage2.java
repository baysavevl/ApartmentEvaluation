package vinhl.testNDN;

import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.NhaDatNhanhApartmentListCrawler;
import vinhl.crawlers.NhaDatNhanhPageCrawler;

public class Stage2 {
    public static void main(String[] args) {
        /*
        String url9 = "https://nhadatnhanh.vn/can-ho-ban-tai-quan-9-tphcm";
        String url7 = "https://nhadatnhanh.vn/can-ho-ban-tai-quan-7-tphcm";
        String url2 = "https://nhadatnhanh.vn/can-ho-ban-tai-quan-2-tphcm";
        String url11 = "https://nhadatnhanh.vn/can-ho-ban-tai-quan-11-tphcm";

        String currentUrl;

        NhaDatNhanhApartmentListCrawler crawler = new NhaDatNhanhApartmentListCrawler();
        NhaDatNhanhPageCrawler nhaDatNhanhPageCrawler = new NhaDatNhanhPageCrawler();
        int page = nhaDatNhanhPageCrawler.getPages(url7);
        System.out.println("Page number = " + page);

        System.out.println(java.time.LocalTime.now());

        for (int i = 0; i < page; i++) {
            System.out.println("I = " + i);
            if (i > 0) {
                currentUrl = url7 + WebsiteConstant.NhaDatNhanh.paging + i;
            } else currentUrl = url7;
            System.out.println("URL = " + currentUrl );
            crawler.getListApartment(currentUrl);
        }

        System.out.println(java.time.LocalTime.now());

        System.out.println("----");
        page = nhaDatNhanhPageCrawler.getPages(url11);
        System.out.println("Page number = " + page);
*/
    }
}
