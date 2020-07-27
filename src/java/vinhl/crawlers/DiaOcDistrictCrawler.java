package vinhl.crawlers;


import vinhl.constant.WebsiteConstant;
import vinhl.utils.XMLChecker;

import javax.servlet.ServletContext;
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


public class DiaOcDistrictCrawler extends BaseCrawler {

    public DiaOcDistrictCrawler(ServletContext context) {
        super(context);
    }

    public DiaOcDistrictCrawler() {
    }


    public Map<String, String> getDistrict(String url) {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();

        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("</select>")) {
                    isEnd = true;
                }

                if (line.contains("id=\"DistrictList\"")) {
                    isFound = true;
                }
                if (isFound) {
                    document += line.trim();
                }
                if (isEnd) {
                    break;
                }

            }
            return stAXParserForDistrict(XMLChecker.refineHtml(document));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(DiaOcDistrictCrawler.class.getName()).log(Level.SEVERE, null, ex);
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


    public Map<String, String> stAXParserForDistrict(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> districtList = new TreeMap<String, String>();
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("option".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("value"));
                    String link = WebsiteConstant.DiaOc.prefixDistrict + attrHref.getValue() + WebsiteConstant.DiaOc.suffixDistrict;
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();
                    if (!character.getData().equalsIgnoreCase(WebsiteConstant.DiaOc.exceptDistrictTag)) {
                        districtList.put(character.getData(), link);
                    }
                    /*
                    Constants.LIST_DISTRICT.add(character.getData());
                    if (!character.getData().equalsIgnoreCase(WebsiteConstant.DiaOc.exceptDistrictTag)) {
                        districtList.put(character.getData(), link);
                    }
                    */
                }
            }
        }
        return districtList;
    }
}
