package vinhl.entities;

import vinhl.dao.ApartmentDAO;
import vinhl.dao.DistrictDAO;
import vinhl.dto.AnalyticDistrict;
import vinhl.filter.ApartmentFilter;
import vinhl.jaxb.Apartment;
import vinhl.utils.DistanceUtil;
import vinhl.utils.GeoCoding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ApartmentManager {
    public ArrayList<Apartment> list;

    public ApartmentManager(ArrayList<Apartment> list) {
        this.list = list;
    }

    public ApartmentManager() {
    }

    public List<Apartment> getRecommend(ApartmentFilter filter, int top) {
        List<Apartment> result = new ArrayList<>();
        List<Apartment> fullList = ApartmentDAO.getAllinDistrict(filter.getId());
        AnalyticDistrict analyticDistrict = DistrictDAO.getAnalyticDistrict(filter.getId());
        float score;

        /*
        if (Objects.isNull(filter.getMaxprice())) {
            filter.setMaxprice((float) analyticDistrict.getMaxPrice());
        }

        if (Objects.isNull(filter.getMinArea())) {
            filter.setMinArea(analyticDistrict.getMinArea());
        }

        if (Objects.isNull(filter.getMinRoom())) {
            filter.setMinRoom(analyticDistrict.getMinRoom());
        }

        if (Objects.isNull(filter.getMaxRestRoom())) {
            filter.setMinRestRoom(analyticDistrict.getMinRest());
        }*/

        float totalWeight = 0;

        for (Apartment entity : fullList) {
            score = 0;
            if (!Objects.isNull(filter)) {
                if (filter.getWeightPrice() > 0) {
                    score += scoringField(entity.getPrice(), filter.getMaxprice(), 2) * filter.getWeightPrice();
                    totalWeight += filter.getWeightPrice();
                }

                if (filter.getWeightArea() > 0) {
                    score += scoringField(entity.getArea(), filter.getMinArea(), 1) * filter.getWeightArea();
                    totalWeight += filter.getWeightArea();

                }

                if (filter.getWeightRoom() > 0) {
                    score += scoringField(entity.getRoom(), filter.getMinRoom(), 1) * filter.getWeightRoom();
                    totalWeight += filter.getWeightRoom();

                }

                if (filter.getWeighRest() > 0) {
                    score += scoringField(entity.getRestRoom(), filter.getMinRestRoom(), 1) * filter.getWeighRest();
                    totalWeight += filter.getWeighRest();

                }
            }

            entity.setScore(score / totalWeight * 100);
            result.add(entity);
        }

        Collections.sort(result);
        result = result.subList(0, top);
        return result;
    }

    private double scoringField(double des, double target, int type) {
        double score = 0;
        if (type == 1) {
            score = Math.min(1, des / target);
        } else {
            score = Math.min(1, target / des);
        }
        return score;
    }

    public List<Apartment> getShortestRoute(String adr1, String adr2, String adr3) throws Exception {
        List<Apartment> result = new ArrayList<>();
        ApartmentDAO dao = new ApartmentDAO();
        List<Apartment> fullList = dao.getAll();
        double lon1 = 0, lon2 = 0, lon3 = 0;
        double lat1 = 0, lat2 = 0, lat3 = 0;
        float score = 0;
        Double[] loc;


        if (!Objects.isNull(adr1) && (adr1.length() > 0)) {
            loc = GeoCoding.getLocation(adr1);
            lon1 = loc[1];
            lat1 = loc[0];
        }

        if (!Objects.isNull(adr2) && (adr2.length() > 0)) {
            loc = GeoCoding.getLocation(adr2);
            lon2 = loc[1];
            lat2 = loc[0];
        }

        if (!Objects.isNull(adr3) && (adr3.length() > 0)) {
            loc = GeoCoding.getLocation(adr3);
            lon3 = loc[1];
            lat3 = loc[0];
        }


        for (Apartment ap : fullList) {
            score = 0;
            if (!Objects.isNull(adr1) && (adr1.length() > 0)) {
                score += DistanceUtil.getDistance(ap.getLatitude(), ap.getLongitude(), lat1, lon1);
            }

            if (!Objects.isNull(adr2) && (adr2.length() > 0)) {
                score += DistanceUtil.getDistance(ap.getLatitude(), ap.getLongitude(),lat2,  lon2);
            }

            if (!Objects.isNull(adr3) && (adr3.length() > 0)) {
                score += DistanceUtil.getDistance(ap.getLatitude(), ap.getLongitude(),lat3, lon3);
            }

            ap.setScore(score);
            result.add(ap);
        }

        Collections.sort(result);
        result = result.subList(result.size() - 4, result.size() - 1);
        //result = result.subList(0, 3);

        return result;
    }


}
