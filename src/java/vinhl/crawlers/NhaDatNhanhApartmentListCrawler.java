package vinhl.crawlers;

import vinhl.constant.WebsiteConstant;
import vinhl.dao.ApartmentDAO;
import vinhl.thread.BaseThread;
import vinhl.utils.XMLChecker;

import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaDatNhanhApartmentListCrawler extends BaseCrawler implements Runnable {
    private String url;
    private int districtId;

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NhaDatNhanhApartmentListCrawler(ServletContext context) {
        super(context);
    }

    public NhaDatNhanhApartmentListCrawler(String url, int districtId) {
        this.url = url;
        this.districtId = districtId;
    }

    /*
    public void getListApartment(String url) {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();
        List<String> threadResult = new ArrayList<>();
        //NhaDatNhanhApartmentDetailCrawler crawlerDetail = new NhaDatNhanhApartmentDetailCrawler();

        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("list-group-item list-footer text-center")) {
                    isEnd = true;
                }

                if (line.contains("list-group-item Product-Free")) {
                    isFound = true;
                }

                if (isFound && line.contains("resultItem")) {
                    document += line.trim();
                    document += "</a>";
                }

                if (isEnd) {
                    break;
                }

            }
            document = "<div>" + document + "</div>";
            threadResult = stAXParserForListApartment(XMLChecker.refineHtml(document));
            for (String item : threadResult) {
                //crawlerDetail.getApartmentDetail(item);
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
    }*/


    public static List<String> stAXParserForListApartment(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        List<String> result = new ArrayList<>();
        String detailLink = "";
        String tagName = "";

        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    detailLink = WebsiteConstant.NhaDatNhanh.prefixPage + attrHref.getValue();
                    result.add(detailLink);
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
            Logger.getLogger(NhaDatNhanhApartmentListCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();
        List<String> threadResult = new ArrayList<>();

        url = getUrl();
        try {
            reader = getBufferedReaderForURL(url);
            if (Objects.isNull(reader)) {
                return;
            }
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("list-group-item list-footer text-center")) {
                    isEnd = true;
                }

                if (line.contains("list-group-item Product-Free")) {
                    isFound = true;
                }

                if (isFound && line.contains("resultItem")) {
                    document += line.trim();
                    document += "</a>";
                }

                if (isEnd) {
                    break;
                }

            }
            document = "<div>" + document + "</div>";
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NhaDatNhanhApartmentListCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            threadResult = stAXParserForListApartment(XMLChecker.refineHtml(document));

            NhaDatNhanhApartmentDetailCrawler crawler = new NhaDatNhanhApartmentDetailCrawler();
            int id = getDistrictId();
            for (String item : threadResult) {
                    crawler.getApartmentDetail(item, id);
                try {
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(NhaDatNhanhApartmentListCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
