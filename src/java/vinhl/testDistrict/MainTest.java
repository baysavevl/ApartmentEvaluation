package vinhl.testDistrict;

import vinhl.constant.Constants;
import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.DistrictCrawler;
import vinhl.dao.DistrictDAO;

import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) throws SQLException {
        String url = WebsiteConstant.District.homePage;
        DistrictCrawler.getDistrict(url);

        for (String item: Constants.LIST_DISTRICT) {
            System.out.println(item + " ;id = " + DistrictDAO.getDistrictId(item));
        }
    }
}
