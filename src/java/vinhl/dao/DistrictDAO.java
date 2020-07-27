package vinhl.dao;

import vinhl.connection.MyConnection;
import vinhl.dto.AnalyticDistrict;
import vinhl.jaxb.District;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictDAO implements Serializable {
    private static Connection conn;
    private static PreparedStatement preStm;
    private static ResultSet rs;

    public static void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCurrentId() {
        int result = 0;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "select IFNULL(max(idDistrict),0) as id from District";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    result = rs.getInt("id");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getDistrictId(String name) {
        int result = 0;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "SELECT idDistrict from District where districtName like ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, name);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    result = rs.getInt("idDistrict");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public static String getDistrictName(int id) {
        String result = "";
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "SELECT districtName FROM district where idDistrict = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, id);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    result = rs.getString("districtName");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public static void saveDistrict(District entity) {
        String sql = "insert into District(idDistrict, districtName) values (?,?);";
        try (Connection conn = MyConnection.getMyConnection();
             PreparedStatement preStm = conn.prepareStatement(sql);) {
            preStm.setInt(1, entity.getId());
            preStm.setString(2, entity.getName());
            preStm.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public static List<String> getAllName() {
        List<String> result = new ArrayList<>();
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "SELECT districtName as name FROM ApartmentEvaluation.District;";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<String> getExistDistrict() {
        List<String> result = new ArrayList<>();
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "SELECT D.districtName as name FROM APARTMENT A JOIN\n" +
                        "DISTRICT D ON A.districtId = D.idDistrict\n" +
                        "Group by A.districtId\n" +
                        "order by D.districtName;";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public static void truncate() throws SQLException {
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "truncate District;";
                PreparedStatement preStm = conn.prepareStatement(sql);
                preStm.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public static List<AnalyticDistrict> getListAnalyticDistrict() {
        List<AnalyticDistrict> result = new ArrayList<>();
        AnalyticDistrict analyticDistrict;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "SELECT districtId as id, D.districtName as name, count(*) as count, min(price) as min_price, max(price) as max_price, min(area) as min_area, max(area) as max_area, " +
                        "min(room) as min_room, max(room) as max_room, min(restroom) as min_rest, max(restroom) as max_rest, " +
                        "avg(A.meanprice) as avg_mean, avg(A.price) as avg_price, avg(area) as avg_area, stddev(price) as std_price  FROM APARTMENT A JOIN\n" +
                        "DISTRICT D ON A.districtId = D.idDistrict\n" +
                        "Group by A.districtId";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    analyticDistrict = new AnalyticDistrict();
                    analyticDistrict.setId(rs.getInt("id"));
                    analyticDistrict.setName(rs.getString("name"));
                    analyticDistrict.setCount(rs.getInt("count"));
                    analyticDistrict.setMinPrice(rs.getDouble("min_price"));
                    analyticDistrict.setMaxPrice(rs.getDouble("max_price"));
                    analyticDistrict.setMinArea(rs.getInt("min_area"));
                    analyticDistrict.setMaxArea(rs.getInt("max_area"));

                    analyticDistrict.setMinRoom(rs.getInt("min_room"));
                    analyticDistrict.setMaxRoom(rs.getInt("max_room"));
                    analyticDistrict.setMinRest(rs.getInt("min_rest"));
                    analyticDistrict.setMaxRest(rs.getInt("max_rest"));

                    analyticDistrict.setAvgePrice(rs.getDouble("avg_price"));
                    analyticDistrict.setAvgMean(rs.getDouble("avg_mean"));
                    analyticDistrict.setStdPrice(rs.getDouble("std_price"));
                    analyticDistrict.setAvgArea(rs.getDouble("avg_area"));

                    double mean = analyticDistrict.getAvgePrice();
                    double std = analyticDistrict.getStdPrice();
                    int n = analyticDistrict.getCount();
                    double mu = 1.96 * std / Math.sqrt(n);

                    analyticDistrict.setRangePriL(mean - mu);
                    analyticDistrict.setRangePriU(mean + mu);

                    result.add(analyticDistrict);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public static AnalyticDistrict getAnalyticDistrict(int id) {
        AnalyticDistrict analyticDistrict = new AnalyticDistrict();
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "SELECT districtId as id, D.districtName as name, count(*) as count, min(price) as min_price, max(price) as max_price, min(area) as min_area, max(area) as max_area, " +
                        "min(room) as min_room, max(room) as max_room, min(restroom) as min_rest, max(restroom) as max_rest, " +
                        "avg(A.meanprice) as avg_mean, avg(A.price) as avg_price, avg(area) as avg_area, stddev(price) as std_price  FROM APARTMENT A JOIN\n" +
                        "DISTRICT D ON A.districtId = D.idDistrict where districtId = ? \n" +
                        "Group by A.districtId";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, id);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    analyticDistrict.setId(rs.getInt("id"));
                    analyticDistrict.setName(rs.getString("name"));
                    analyticDistrict.setCount(rs.getInt("count"));
                    analyticDistrict.setMinPrice(rs.getDouble("min_price"));
                    analyticDistrict.setMaxPrice(rs.getDouble("max_price"));
                    analyticDistrict.setMinArea(rs.getInt("min_area"));
                    analyticDistrict.setMaxArea(rs.getInt("max_area"));

                    analyticDistrict.setMinRoom(rs.getInt("min_room"));
                    analyticDistrict.setMaxRoom(rs.getInt("max_room"));
                    analyticDistrict.setMinRest(rs.getInt("min_rest"));
                    analyticDistrict.setMaxRest(rs.getInt("max_rest"));

                    analyticDistrict.setAvgePrice(rs.getDouble("avg_price"));
                    analyticDistrict.setAvgMean(rs.getDouble("avg_mean"));
                    analyticDistrict.setStdPrice(rs.getDouble("std_price"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return analyticDistrict;
    }


}
