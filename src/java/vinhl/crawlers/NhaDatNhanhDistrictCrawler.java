package vinhl.crawlers;

import vinhl.constant.WebsiteConstant;
import vinhl.thread.BaseThread;
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

public class NhaDatNhanhDistrictCrawler extends BaseCrawler {

    public NhaDatNhanhDistrictCrawler(ServletContext context) {
        super(context);
    }

    public NhaDatNhanhDistrictCrawler() {
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
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("</div>")) {
                    isEnd = true;
                }

                if (count == 2) {
                    isFound = true;
                }

                if (line.contains("row list-group-item active")) {
                    count++;
                }

                if (isFound) {
                    document += line.trim();
                }
                if (isEnd) {
                    break;
                }

            }
            document = "<div>" + document;
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NhaDatNhanhDistrictCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    String link = WebsiteConstant.NhaDatNhanh.prefixPage + attrHref.getValue();
                    event = (XMLEvent) eventReader.next();
                    Characters character = event.asCharacters();
                    districtList.put(character.getData(), link);
                }
            }
        }
        return districtList;
    }
}
