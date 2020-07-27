package vinhl.testDistrict;

import vinhl.entities.ApartmentManager;
import vinhl.filter.ApartmentFilter;
import vinhl.jaxb.Apartment;

import java.util.List;
import vinhl.dao.DistrictDAO;

public class TestSuggestion {
    public static void main(String[] args) {
        ApartmentFilter filter = new ApartmentFilter();
        filter.setMaxprice(10000);
        filter.setMinArea(70);
        filter.setMinRoom(2);
        filter.setMinRestRoom(2);
        filter.setWeightPrice(50);
        filter.setWeightRoom(3);
        filter.setWeightArea(3);
        filter.setWeighRest(3);
        filter.setId(19);


        ApartmentManager am = new ApartmentManager();

        List<Apartment> result = am.getRecommend(filter, 3);
        for (Apartment ap: result) {
            System.out.println(ap.getName());
            System.out.println(ap.getScore());
            System.out.println();
        }
    }
}
