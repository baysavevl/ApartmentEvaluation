package vinhl.crawlers;

import vinhl.constant.WebsiteConstant;
import vinhl.dao.DistrictDAO;
import vinhl.utils.RefineHelper;
import vinhl.utils.StringHelper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NhaDatSoDistrictGenerator {

    public Map<String, String> getDistrict() {

        Map<String, String> result = new TreeMap<>();

        DistrictCrawler.getDistrict(WebsiteConstant.District.homePage);
        String districtName;
        String link;
        List<String> listDistrict = DistrictDAO.getAllName();
        for (String iterator : listDistrict) {
            districtName = iterator;
            districtName = RefineHelper.removeNeedlessPrefixDistrict(districtName);
            districtName = StringHelper.decodeUTF8(districtName);
            districtName = districtName.replace(" ", "-").toLowerCase();
            link = WebsiteConstant.NhaDatSo.prefixPage + WebsiteConstant.NhaDatSo.prefixDistrict + districtName + WebsiteConstant.NhaDatSo.suffixDistrict;
            result.put(iterator, link);
        }
        return result;
    }
}
