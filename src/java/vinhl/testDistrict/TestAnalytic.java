/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhl.testDistrict;

import java.util.List;
import vinhl.dao.DistrictDAO;
import vinhl.dto.AnalyticDistrict;

/**
 *
 * @author Vinh
 */
public class TestAnalytic {
    public static void main(String[] args) {
        List<AnalyticDistrict> listResult = DistrictDAO.getListAnalyticDistrict();
        for (AnalyticDistrict analyticDistrict : listResult) {
            System.out.println(analyticDistrict.getName());
            System.out.println(analyticDistrict.getAvgePrice());
        }
    }
}
