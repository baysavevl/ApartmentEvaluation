package vinhl.test;

import vinhl.constant.Constants;
import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.BaseCrawler;
import vinhl.utils.XMLChecker;

import javax.swing.text.html.HTMLDocument;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
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
            //col-12 float-left px-0 d-block d-lg-none post-content-tit//</div>
            boolean isFoundP2 = false;
            //card-text text-list pt-1

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
            //document = document.replaceAll("</i>", "");
            //document = document.replaceAll("</p>", "</i></p>");
            stAXParserForApartmentDetail(XMLChecker.refineHtml(document));
            return XMLChecker.refineHtml(document);
            //return stAXParserForApartmentDetail(XMLChecker.refineHtml(document));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (XMLStreamException ex) {
//            Logger.getLogger(DiaOcDistrictCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException e) {
            e.printStackTrace();
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

    public static void stAXParserForApartmentDetail(String document) throws UnsupportedEncodingException, XMLStreamException, ClassCastException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);


        String price = "";
        String address = "";
        String area = "";
        String roomQ = "";
        String resRQ = "";

        boolean isStart = false;
        boolean isEnd = false;

        String className = "";
        String tagName = "";
        Characters character;

        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if("p".equals(tagName)){
                    //part 1
                    if (startElement.getAttributeByName(new QName("class")) != null) {
                     Attribute attrHref = startElement.getAttributeByName(new QName("class"));
                        event = eventReader.nextTag();
                        event = eventReader.nextTag();
                        event = eventReader.nextEvent();
                        event = eventReader.nextTag();
                        event = eventReader.nextTag();
                        event = eventReader.nextEvent();
                        Characters p = event.asCharacters();
                        event = eventReader.nextTag();
                        //event = eventReader.nextTag();
                        event = eventReader.nextEvent();
                        Characters l = event.asCharacters();

                        System.out.println(p.getData() + " " + l.getData());
                        /*
                        if (attrHref.getValue().isEmpty()) {
                            event = (XMLEvent) eventReader.next();
                            startElement = event.asStartElement();
                            tagName = startElement.getName().getLocalPart();
                            if ("i".equals(tagName)) {
                                isStart = true;
                                attrHref = startElement.getAttributeByName(new QName("class"));
                                className = attrHref.getValue();
                                if (className.equalsIgnoreCase(WebsiteConstant.NhaDatSo.classPrice)) {
                                    //event = (XMLEvent) eventReader.nextTag();
                                    //character = event.asCharacters();
                                    //price = character.getData();
                                    System.out.println(price);
                                }


                            }
                        } */
                    }


                } else
                if ("span".equals(tagName)) {

                }
            }
        }
    }



    public static void main(String[] args) {

        String url = "https://nhadatso.com/0386477699/vinhomes-grand-park-quan-9-so-huu-ngay-can-ho-dang-cap-singapore-lien-he-0386477699-mr-phuc-x31kjm";
        String url2 = "https://nhadatso.com/0909888034/ban-chung-cu-van-phong-442m2-mat-tien-duong-77-nguyen-thi-thap-quan-7-33-ty-x2rhtr";
        String onlyArea = "https://nhadatso.com/0938061243/nhan-giu-cho-uu-tien-nha-pho-shop-biet-thu-can-ho-vincity-cam-ket-lh-0938061243-x31d6l";
        String url3 = "https://nhadatso.com/0903361810/ban-can-ho-xi-grand-court-gia-tot-2-phong-ngu-tu-39-tycan-3-phong-ngu-tu-51-ty-x321m2";
        String res = NDS_C_T4.getApartmentDetail(url2);
        System.out.println(res);
    }
}
