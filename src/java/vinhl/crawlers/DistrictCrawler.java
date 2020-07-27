package vinhl.crawlers;

import vinhl.constant.Constants;
import vinhl.dao.DistrictDAO;
import vinhl.jaxb.District;
import vinhl.utils.NumberHelper;
import vinhl.utils.XMLChecker;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DistrictCrawler extends BaseCrawler {

    public static void getDistrict(String url) {
        if (!Objects.isNull(Constants.LIST_DISTRICT)) {
            Constants.LIST_DISTRICT.removeAll(Constants.LIST_DISTRICT);
        }
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();

        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isBegin = false;
            boolean isStop = true;
            boolean isEnd = false;
            boolean isFound = false;
            int mark = 0;
            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("</tbody>")) {
                    isEnd = true;
                }

                if (line.contains("DANH MỤC QUẬN HUYỆN")) {
                    isFound = true;
                    document += "<div>";
                }

                if (isFound && isStop && line.contains("<tr")) {
                    isBegin = true;
                    isStop = false;
                }

                if (isBegin) {
                    document += line.trim();
                }

                if (!isStop && isBegin && line.contains("</tr>")) {
                    isStop = true;
                    isBegin = false;
                }

                if (isEnd) {
                    document += line.trim();
                    break;
                }

            }
            //System.out.println(XMLChecker.refineHtml(document));
            //System.out.println(XMLChecker.refineHtml(document));
            stAXParserForDistrict(XMLChecker.refineHtml(document));
            List<String> result = DistrictDAO.getAllName();
            Constants.LIST_DISTRICT = new ArrayList<>();
            Constants.LIST_DISTRICT.addAll(result);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(DiaOcDistrictCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public static void stAXParserForDistrict(String document) throws UnsupportedEncodingException, XMLStreamException, SQLException {
        document = document.trim();
        DistrictDAO.truncate();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<Integer, String> districtList = new TreeMap<Integer, String>();
        int count = -1;
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("tr".equals(tagName)) {
                    count++;
                    //Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    //String link = WebsiteConstant.NhaDatNhanh.prefixPage + attrHref.getValue();
                    if (count > 0) {
                        eventReader.next();
                        int id = NumberHelper.extractNumber(eventReader.next().toString());
                        eventReader.next();

                        eventReader.next();
                        eventReader.next();
                        eventReader.next();

                        eventReader.next();
                        String name = eventReader.next().toString();
                        eventReader.next();

                        District district = new District();
                        district.setId(id);
                        district.setName(name);

                        DistrictDAO.saveDistrict(district);
                        //System.out.println("id =" + id);
                        //System.out.println("name =" + name);
                    }

                }
            }
        }
    }
}
