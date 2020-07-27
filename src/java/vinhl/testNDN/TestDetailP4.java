package vinhl.testNDN;

import vinhl.constant.Constants;
import vinhl.crawlers.NhaDatNhanhApartmentDetailCrawler;
import vinhl.crawlers.NhaDatNhanhApartmentListCrawler;
import vinhl.crawlers.NhaDatSoApartmentDetailCrawler;
import vinhl.dao.ApartmentDAO;

public class TestDetailP4 {
    public static void main(String[] args) {
        Constants.ID_APARTMENT = ApartmentDAO.getCurrentId();

        String url = "https://nhadatnhanh.vn/can-ho-ban-chung-cu-cao-cap/tai-chinh-3-ty-kenh-dau-tu-sieu-loi-nhuan-mua-covid19-106829.html";
        String url2 = "https://nhadatnhanh.vn/can-ho-ban-chung-cu-cao-cap/tai-chinh-3-ty-kenh-dau-tu-sieu-loi-nhuan-mua-covid19-106829.html";

        String url3 = "https://nhadatnhanh.vn/can-ho-ban-chung-cu-cao-cap/can-ho-chung-cu-bau-cat-2-can-ban-voi-dien-tich-50m2-2pn-1wc-111063.html";
        //NhaDatNhanhApartmentDetailCrawler crawler = new NhaDatNhanhApartmentDetailCrawler(url3);

        NhaDatNhanhApartmentDetailCrawler crawlerDetail = new NhaDatNhanhApartmentDetailCrawler();
        //crawlerDetail.getApartmentDetail(url2);

        String list1 = "https://nhadatnhanh.vn/can-ho-ban-tai-quan-9-tphcm";
        Thread listThread = new Thread(new NhaDatNhanhApartmentListCrawler(list1, 4));
        listThread.start();
       // Thread crawlingThread = new Thread(new NhaDatNhanhApartmentDetailCrawler(url2));
        //crawlingThread.start();
    }
}
