package vinhl.crawlers;

import vinhl.constant.WebsiteConstant;
import vinhl.dao.ApartmentDAO;
import vinhl.thread.BaseThread;
import vinhl.utils.XMLChecker;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaDatSoApartmentListCrawler extends BaseCrawler implements Runnable {

    private String url;
    private int districtId;

    public NhaDatSoApartmentListCrawler(String url, int districtId) {
        this.url = url;
        this.districtId = districtId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    /*
    public String getListApartment() {
        String url = getUrl();
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
                if (isFound && line.contains("class=\"pagination pagination-sm float-right\"")) {
                    isEnd = true;
                }

                if (line.contains("id=\"crb-choose-show\"")) {
                    isFound = true;
                    document += line.trim();
                }

                if (isFound && line.contains("class=\"list-img\"")) {
                    document += line.trim();
                    isOpenImg = true;
                }

                if (isOpenImg) {
                    document += line.trim();
                }

                if (isOpenImg && line.contains("</div>")) {
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
    */


    public static Map<String, String> stAXParserForListApartment(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();

        Map<String, String> result = new TreeMap<>();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        boolean isApartmentLink = true;
        boolean isImgLink = false;
        String detailLink = "";
        String imgLink = "";
        String tagName = "";

        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if (isApartmentLink && "img".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("data-src"));
                    imgLink = attrHref.getValue();
                    isImgLink = true;
                    isApartmentLink = false;
                } else if (isImgLink && "a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    detailLink = WebsiteConstant.NhaDatSo.prefixPage + attrHref.getValue();
                    result.put(detailLink, imgLink);
                    isApartmentLink = true;
                    isImgLink = false;
                }
            }
        }

        try {
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(NhaDatSoApartmentListCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    @Override
    public void run() {
        String url = getUrl();
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
                if (isFound && line.contains("class=\"pagination pagination-sm float-right\"")) {
                    isEnd = true;
                }

                if (line.contains("id=\"crb-choose-show\"")) {
                    isFound = true;
                    document += line.trim();
                }

                if (isFound && line.contains("class=\"list-img\"")) {
                    document += line.trim();
                    isOpenImg = true;
                }

                if (isOpenImg) {
                    document += line.trim();
                }

                if (isOpenImg && line.contains("</div>")) {
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
            Map<String, String> threadResult = new TreeMap<>();
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NhaDatSoApartmentListCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            districtId = getDistrictId();
            threadResult = stAXParserForListApartment(XMLChecker.refineHtml(document));
            NhaDatSoApartmentDetailCrawler crawler = new NhaDatSoApartmentDetailCrawler();
            for (Map.Entry<String, String> entry : threadResult.entrySet()) {
                if (!ApartmentDAO.isExisted(entry.getKey())) {
                    crawler.getApartmentDetail(entry.getKey(), entry.getValue(), districtId);
                }
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
    }
}
