package vinhl.testNDN;

import vinhl.constant.AppConstant;
import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.NhaDatNhanhDistrictCrawler;
import vinhl.crawlers.NhaDatNhanhPageCrawler;

import java.time.LocalTime;
import java.util.Map;

public class Stage1 {
    public static void main(String[] args) {
        String url = WebsiteConstant.NhaDatNhanh.mainPage;
        NhaDatNhanhDistrictCrawler nhaDatNhanhDistrictCrawler = new NhaDatNhanhDistrictCrawler();
        NhaDatNhanhPageCrawler nhaDatNhanhPageCrawler = new NhaDatNhanhPageCrawler();
        //NhaDatNhanhApartmentListCrawler crawler = new NhaDatNhanhApartmentListCrawler();
        String urlDetail;
        int page;

        System.out.println(java.time.LocalTime.now());

        LocalTime timeStart = LocalTime.now();
        String currentUrl;
        Map<String, String> test;
        test = nhaDatNhanhDistrictCrawler.getDistrict(url);
        for (Map.Entry<String, String> entry : test.entrySet()) {
            urlDetail = entry.getValue();
            System.out.println(entry.getKey() + ":" + urlDetail);
            //page = nhaDatNhanhPageCrawler.getPages(urlDetail);

            page = Math.min(1, 6);
            for (int i = 0; i < page; i++) {
                System.out.println("I = " + i);
                if (i > 0) {
                    currentUrl = urlDetail + WebsiteConstant.NhaDatNhanh.paging + i;
                } else currentUrl = urlDetail;
                System.out.println("URL = " + currentUrl);
                //crawler.getListApartment(currentUrl);
            }

            System.out.println("NEW DISTRICT");
        }
        System.out.println(java.time.LocalTime.now());
        LocalTime timeEnd = LocalTime.now();

        System.out.println(timeStart + " -  " + timeEnd);
        //System.out.println(AppConstant.count);
    }
}
