package vinhl.crawlers;

import vinhl.constant.Constants;
import vinhl.constant.WebsiteConstant;
import vinhl.utils.NumberHelper;
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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhaDatSoPageCrawler extends BaseCrawler {
    public NhaDatSoPageCrawler(ServletContext context) {
        super(context);
    }

    public NhaDatSoPageCrawler() {
    }

    public int getPages(String url) {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();
        Map<String, String> listPage = new HashMap<>();
        int lastPage = 1;
        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            while ((line = reader.readLine()) != null) {
                if(isFound && line.contains("</ul>")){
                    isEnd = true;
                }

                if (line.contains("pagination pagination-sm float-right")) {
                    isFound = true;
                }
                if (isFound) {
                    document += line.trim();
                }
                if (isEnd) {
                    break;
                }
            }
            if (isFound) {
                String[] list = document.split("</ul>");
                document = XMLChecker.refineHtml(list[0]);
                //System.out.println(document);
                lastPage = getLastPage(XMLChecker.refineHtml(document));
            } else
                lastPage = 0;
            return lastPage;
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
        return lastPage;
    }

    private int getLastPage(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        String tagName = "";
        String link = "";
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    link = attrHref == null ? "" : attrHref.getValue();
                }
            }//End if start element
        }//End while

        return NumberHelper.getNumberInString(link, 1);
    }
}
