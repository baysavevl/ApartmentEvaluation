package vinhl.test;

import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.BaseCrawler;
import vinhl.utils.XMLChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NDS_C_T4 extends BaseCrawler{
    public static String getApartmentDetail(String url) {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();

        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            //col-xs-12 col-sm-12 col-md-12 col-lg-6 pl-2 pr-2 float-left crb_contain_mid contain_news_detail_mid

            boolean isFoundP1 = false;
            //col-12 float-left px-0 d-block d-lg-none post-content-tit
            //</div>

            boolean isFoundP2 = false;
            //card-text text-list pt-1
            //</div>

            while ((line = reader.readLine()) != null) {
                if(isFound && line.contains("</section>")){
                    isEnd = true;
                }

                if (line.contains("col-xs-12 col-sm-12 col-md-12 col-lg-6 pl-2 pr-2 float-left crb_contain_mid contain_news_detail_mid")) {
                    isFound = true;
                    document += line.trim();
                }

                if (isFound && line.contains("col-12 float-left px-0 d-block d-lg-none post-content-tit")) {
                    document += line.trim();
                    isFoundP1 = true;
                }

                if (isFoundP1) {
                    document += line.trim();
                    if (line.contains("</div>")) {
                        isFoundP1 = false;
                    }
                }

                if (isFound && line.contains("card-text text-list pt-1")) {
                    document += line.trim();
                    isFoundP2 = true;
                }

                if (isFoundP2) {
                    document += line.trim();
                    if (line.contains("</div>")) {
                        isFoundP2 = false;
                    }
                }

                if (isEnd) {
                    break;
                }

            }
            return XMLChecker.refineHtml(document);
            //return stAXParserForDistrict(XMLChecker.refineHtml(document));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (XMLStreamException ex) {
//            Logger.getLogger(DiaOcDistrictCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void main(String[] args) {

        String url = "https://nhadatso.com/0386477699/vinhomes-grand-park-quan-9-so-huu-ngay-can-ho-dang-cap-singapore-lien-he-0386477699-mr-phuc-x31kjm";
        String url2 = "https://nhadatso.com/0909888034/ban-chung-cu-van-phong-442m2-mat-tien-duong-77-nguyen-thi-thap-quan-7-33-ty-x2rhtr";
        String res = NDS_C_T4.getApartmentDetail(url2);
        System.out.println(res);
    }
}
