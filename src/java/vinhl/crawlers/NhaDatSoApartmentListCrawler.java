package vinhl.crawlers;

import vinhl.constant.Constants;
import vinhl.constant.WebsiteConstant;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaDatSoApartmentListCrawler extends BaseCrawler{

    public String getListApartment(String url) {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();

        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            boolean isOpenImg = false;
            boolean isContent = false;
            while ((line = reader.readLine()) != null) {
                if(isFound && line.contains("class=\"pagination pagination-sm float-right\"")){
                    isEnd = true;
                }

                if (line.contains("id=\"crb-choose-show\"")) {
                    isFound = true;
                    document += line.trim();
                }
                /*
                if (isFound && line.contains("card-block pl-2 pr-2 post-content-detail")) {
                    document += line.trim();

                }*/

                if (isFound && line.contains("class=\"list-img\"")) {
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

                if (line.contains("text-black line-limit-3")) {
                    isContent = true;
                }

                if (isContent && line.contains("</a>")) {
                    document += line.trim();
                    isContent = false;
                }

                if (isContent) {
                    document += line.trim();
                }

                if (isEnd) {
                    break;
                }

            }
            stAXParserForListApartment(XMLChecker.refineHtml(document));
            return XMLChecker.refineHtml(document);
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


    public static void stAXParserForListApartment(String document) throws UnsupportedEncodingException, XMLStreamException{
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        boolean isApartmentLink = true;
        boolean isImgLink = false;
        String apartmentName = "";
        String detailLink = "";
        String imgLink = "";
        String tagName = "";

        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if(isApartmentLink && "img".equals(tagName)){
                    Attribute attrHref = startElement.getAttributeByName(new QName("data-src"));
                    imgLink= attrHref.getValue();
                    isImgLink = true;
                    isApartmentLink = false;
                } else
                if (isImgLink && "a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    detailLink = WebsiteConstant.NhaDatSo.prefixPage + attrHref.getValue();
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();
                    apartmentName = character.getData();
                    Constants.ID_APARTMENT++;
                    System.out.println(Constants.ID_APARTMENT + "----Start-----");
                    System.out.println("link = " + detailLink);
                    System.out.println("name = " + apartmentName);
                    System.out.println("img = " + imgLink);
                    isApartmentLink = true;
                    isImgLink = false;
                }
            }
        }
    }
}
