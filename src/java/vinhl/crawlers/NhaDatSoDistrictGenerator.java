package vinhl.crawlers;

import vinhl.constant.Constants;
import vinhl.constant.WebsiteConstant;
import vinhl.utils.RefineHelper;
import vinhl.utils.StringHelper;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class NhaDatSoDistrictGenerator {
    public Map<String, String> getDistrict() {
        //DiaOcDistrictCrawler diaOcDistrictCrawler = new DiaOcDistrictCrawler();
        Map<String, String> result = new TreeMap<>();

        String districtName;
        String link;
        String[] listDistrict = Constants.LIST_DISTRICT;
        for (String iterator: listDistrict) {
            districtName = iterator;
            districtName = RefineHelper.removeNeedlessPrefixDistrict(districtName);
            districtName = StringHelper.decodeUTF8(districtName);
            districtName = districtName.replace(" ", "-").toLowerCase();
            link = WebsiteConstant.NhaDatSo.prefixPage + WebsiteConstant.NhaDatSo.prefixDistrict + districtName + WebsiteConstant.NhaDatSo.suffixDistrict;

            result.put(iterator, link);
        }

        /*
        Map<String, String> districtMap = diaOcDistrictCrawler.getDistrict(WebsiteConstant.DiaOc.urlDiaOcHomePage);
        for (Map.Entry<String, String> entry : districtMap.entrySet()) {
            districtName = RefineHelper.removeNeedlessPrefixDistrict(entry.getKey());
            districtName = StringHelper.decodeUTF8(districtName);
            districtName = districtName.replace(" ", "-").toLowerCase();
            link = WebsiteConstant.NhaDatSo.prefixPage + WebsiteConstant.NhaDatSo.prefixDistrict + districtName + WebsiteConstant.NhaDatSo.suffixDistrict;

            result.put(entry.getKey(), link);
        }*/
        return  result;
    }
}
