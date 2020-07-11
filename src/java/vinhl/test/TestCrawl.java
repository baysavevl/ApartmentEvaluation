package vinhl.test;

import vinhl.crawlers.BaseCrawler;
import vinhl.crawlers.DiaOcDistrictCrawler;
import vinhl.utils.XMLChecker;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestCrawl {
    protected BufferedReader getBufferedReaderForURL(String urlString) throws MalformedURLException, UnsupportedEncodingException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    public String getPages(String url) {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();
        Map<String, String> listPage = new HashMap<>();
        int lastPage = 1;
        String document = "";

        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
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
            String[] list = document.split("</ul>");
            document = XMLChecker.refineHtml(list[0]);
            System.out.println(document);
            //return stAXParserForPage(XMLChecker.refineHtml(document));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return document;
    }
}
