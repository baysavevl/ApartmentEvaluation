package vinhl.test;

import vinhl.constant.Constants;
import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.DiaOcDistrictCrawler;
import vinhl.crawlers.NhaDatSoDistrictGenerator;
import vinhl.crawlers.NhaDatSoPageCrawler;
import vinhl.utils.UrlValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
import java.util.regex.Pattern;

import java.util.Map;

public class MainTest {
    public static void main(String[] args) throws IOException {
        Map<String, String> test;

        //String url = "http://vinhluu.com/";

        String url = "https://nhadatso.com/090.472.4372/can-ho-happy-one-quan-12-mua-truc-tiep-tu-chu-dau-tu-x32o63";
        //System.out.println(UrlValidator.checkValidate(url));
        DiaOcDistrictCrawler diaOcDistrictCrawler = new DiaOcDistrictCrawler();
        NhaDatSoDistrictGenerator nhaDatSoDistrictGenerator = new NhaDatSoDistrictGenerator();
        test = diaOcDistrictCrawler.getDistrict(WebsiteConstant.DiaOc.urlDiaOcHomePage);

        for (String t: Constants.LIST_DISTRICT
             ) {
            System.out.println(t);
        }
        //String doc= diaOcDistrictCrawler.getDistrictStr(WebsiteConstant.DiaOc.urlDiaOcHomePage);

        //System.out.println(doc);

        url = "https://nhadatso.com/nha-dat-ban-can-ho-chung-cu-tai-quan-9-ho-chi-minh/";

//        TestCrawl testCrawl = new TestCrawl();
//        String text = testCrawl.getPages(url);

        NhaDatSoPageCrawler nhaDatSoPageCrawler = new NhaDatSoPageCrawler();
        //int numberPage = nhaDatSoPageCrawler.getPages(url);

        //System.out.println(numberPage);
        //System.out.println(text);
        /*
        String str1 = "Quận 9";
        String str = "Quận Binh Thanh";

        System.out.println(str.matches(".*\\d.*"));
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String res = pattern.matcher(temp).replaceAll("");
        System.out.println(res.replace(" ","-").toLowerCase());*/

        /*
        String urlString = WebsiteConstant.DiaOc.urlDiaOcHomePage;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = "";
        String document = "";
        boolean isStart = false;
        boolean isFound = false;
        while ((line = reader.readLine()) != null) {
                document += line.trim();
        }
        System.out.println(document);*/

    }
}
