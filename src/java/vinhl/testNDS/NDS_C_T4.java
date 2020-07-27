package vinhl.testNDS;

import vinhl.crawlers.BaseCrawler;
import vinhl.crawlers.NhaDatSoApartmentDetailCrawler;

import javax.xml.stream.XMLStreamException;
import java.io.UnsupportedEncodingException;

public class NDS_C_T4 extends BaseCrawler {

    public static void main(String[] args) {

        String url = "https://nhadatso.com/0386477699/vinhomes-grand-park-quan-9-so-huu-ngay-can-ho-dang-cap-singapore-lien-he-0386477699-mr-phuc-x31kjm";
        String url2 = "https://nhadatso.com/0909888034/ban-chung-cu-van-phong-442m2-mat-tien-duong-77-nguyen-thi-thap-quan-7-33-ty-x2rhtr";
        String onlyArea = "https://nhadatso.com/0938061243/nhan-giu-cho-uu-tien-nha-pho-shop-biet-thu-can-ho-vincity-cam-ket-lh-0938061243-x31d6l";
        String url3 = "https://nhadatso.com/0903361810/ban-can-ho-xi-grand-court-gia-tot-2-phong-ngu-tu-39-tycan-3-phong-ngu-tu-51-ty-x321m2";
        String url4 = "https://nhadatso.com/0941916367/can-ban-can-ho-4s2-linh-dong-thu-duc-nha-dep-ban-cong-sat-gia-tot-cho-nguoi-thien-tri-mua-x2mt4h";


        String testUrl = "https://nhadatso.com/0773340340/du-an-can-ho-gay-du-luan-bao-chi-doi-dien-ben-xe-mien-dong-moi-x31e5s";
        NhaDatSoApartmentDetailCrawler crawler = new NhaDatSoApartmentDetailCrawler();
        crawler.getApartmentDetail(url2, "", 4);
    }
}
