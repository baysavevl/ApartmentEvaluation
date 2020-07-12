package vinhl.crawlers;

import vinhl.thread.BaseThread;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.ServletContext;

public class BaseCrawler {
    private ServletContext context;

    public BaseCrawler(ServletContext context) {
        this.context = context;
    }

    public BaseCrawler() {
    }

    public ServletContext getContext() {
        return context;
    }

    protected static BufferedReader getBufferedReaderForURL(String urlString) throws MalformedURLException, UnsupportedEncodingException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    protected static XMLEventReader parseStringToXMLEventReader(String xmlSection) throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        return reader;
    }
}
