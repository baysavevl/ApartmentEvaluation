package vinhl.crawlers;

import vinhl.constant.Constants;
import vinhl.constant.WebsiteConstant;
import vinhl.thread.BaseThread;
import vinhl.utils.NumberHelper;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaDatNhanhPageCrawler extends BaseCrawler implements Runnable {
    private String url;
    private int districtId;


    public NhaDatNhanhPageCrawler(String url, int districtId) {
        this.url = url;
        this.districtId = districtId;
    }

    public NhaDatNhanhPageCrawler() {
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
            boolean isPaged = false;
            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("</ul>")) {
                    isEnd = true;
                }

                if (line.contains("pagination")) {
                    isFound = true;
                }

                if (isFound) {
                    document += line.trim();
                    if (!isPaged) {
                        if (line.contains("pager-item")) {
                            isPaged = true;
                        }
                    }
                }

                if (isEnd) {
                    break;
                }
            }
            if (isPaged) {
                //System.out.println(XMLChecker.refineHtml(document));
                lastPage = Math.min(getLastPage(XMLChecker.refineHtml(document)), Constants.MAX_PAGE);
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
    }*/

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
                    if (startElement.getAttributeByName(new QName("href")) != null) {
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        link = character.getData();
                    }
                } else {
                    if ("li".equals(tagName)) {
                        Attribute attrHref = startElement.getAttributeByName(new QName("class"));
                        if (attrHref.getValue().equalsIgnoreCase("pager-next")) {
                            break;
                        }
                    }
                }
            }//End if start element
        }//End while
        try {
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(NhaDatNhanhPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return NumberHelper.extractNumber(link);
    }

    @Override
    public void run() {
        url = getUrl();
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();
        Map<String, String> listPage = new HashMap<>();
        int lastPage = 1;
        try {
            reader = getBufferedReaderForURL(url);
            if (Objects.isNull(reader)) {
                return;
            }
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            boolean isPaged = false;
            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("</ul>")) {
                    isEnd = true;
                }

                if (line.contains("pagination")) {
                    isFound = true;
                }

                if (isFound) {
                    document += line.trim();
                    if (!isPaged) {
                        if (line.contains("pager-item")) {
                            isPaged = true;
                        }
                    }
                }

                if (isEnd) {
                    break;
                }
            }
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NhaDatNhanhPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (isPaged) {
                lastPage = Math.min(getLastPage(XMLChecker.refineHtml(document)), Constants.MAX_PAGE);
            } else
                lastPage = 0;

            String currentUrl;
            Thread crawlingList;
            int disId = getDistrictId();
            for (int i = 0; i < lastPage; i++) {
                if (i > 0) {
                    currentUrl = url + WebsiteConstant.NhaDatNhanh.paging + i;
                } else currentUrl = url;

                crawlingList = new Thread(new NhaDatNhanhApartmentListCrawler(currentUrl, disId));
                try {
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(NhaDatNhanhPageCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
                crawlingList.start();
            }

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
    }
}
