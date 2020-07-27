package vinhl.testNDS;

import vinhl.constant.Constants;
import vinhl.crawlers.NhaDatSoDistrictGenerator;
import vinhl.crawlers.NhaDatSoPageCrawler;
import vinhl.dao.ApartmentDAO;
import vinhl.dao.DistrictDAO;

import java.util.Map;

public class NDS_C_T2 {
    public static void main(String[] args) {
        Map<String, String> test;
        Constants.ID_APARTMENT = ApartmentDAO.getCurrentId();
        Constants.LIST_DISTRICT = DistrictDAO.getAllName();

        NhaDatSoDistrictGenerator nhaDatSoDistrictGenerator = new NhaDatSoDistrictGenerator();

        test = nhaDatSoDistrictGenerator.getDistrict();

        for (Map.Entry<String, String> entry : test.entrySet()) {
            //numberPage = nhaDatSoPageCrawler();
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            //System.out.println("id = " + DistrictDAO.getDistrictId(entry.getKey()));
            Thread districtCrawling = new Thread(new NhaDatSoPageCrawler(entry.getValue(), DistrictDAO.getDistrictId(entry.getKey())));
            districtCrawling.start();
            System.out.println("NEW DISTRICT");
        }

    }
}
