package vinhl.test;

import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.BaseCrawler;
import vinhl.utils.XMLChecker;

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
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NDS_C_T3 extends BaseCrawler{

    public static String getListApartment(String url) {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();

        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            boolean isOpenImg = false;
            while ((line = reader.readLine()) != null) {
                if(isFound && line.contains("class=\"pagination pagination-sm float-right\"")){
                    isEnd = true;
                }

                if (line.contains("id=\"crb-choose-show\"")) {
                    isFound = true;
                    document += line.trim();
                }
                if (isFound && line.contains("card-block pl-2 pr-2 post-content-detail")) {
                    document += line.trim();

                }

                if (line.contains("class=\"list-img\"")) {
                    document += line.trim();
                    isOpenImg = true;
                }

                if (isOpenImg) {
                    document += line.trim();
                }

                if(isOpenImg && line.contains("</div>")) {
                    document += line.trim();
                    isOpenImg = false;
                    document += "</div>";
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


    //HAVEN'T DONE
    public Map<String, String> stAXParserForListApartment(String document) throws UnsupportedEncodingException, XMLStreamException{
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> districtList = new TreeMap<String, String>();

        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if("option".equals(tagName)){
                    Attribute attrHref = startElement.getAttributeByName(new QName("value"));
                    String link = WebsiteConstant.DiaOc.prefixDistrict + attrHref.getValue() + WebsiteConstant.DiaOc.suffixDistrict;
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();
                    if (!character.getData().equalsIgnoreCase(WebsiteConstant.DiaOc.exceptDistrictTag)) {
                        districtList.put(character.getData(), link);
                    }
                }
            }
        }
        return districtList;
    }

    public static void main(String[] args) {
        String url = "https://nhadatso.com/nha-dat-ban-can-ho-chung-cu-tai-quan-9-ho-chi-minh/?p=3";
        String res = NDS_C_T3.getListApartment(url);
        System.out.println(res);
    }
}
