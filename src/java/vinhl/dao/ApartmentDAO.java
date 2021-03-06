package vinhl.dao;

import vinhl.connection.MyConnection;
import vinhl.jaxb.Apartment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApartmentDAO implements Serializable {
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
            //e.printStackTrace();
        }
    }

    public static void saveApartment(Apartment entity) {
        String sql = "insert into Apartment (idApartment, nameApartment, imgUrl, webUrl, price, meanPrice, districtId, room, restRoom, address, area, longitude, latitude) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (Connection conn = MyConnection.getMyConnection();
             PreparedStatement preStm = conn.prepareStatement(sql);) {
            preStm.setInt(1, entity.getId());
            preStm.setString(2, entity.getName());
            preStm.setString(3, entity.getImgUrl());
            preStm.setString(4, entity.getWebUrl());
            preStm.setDouble(5, entity.getPrice());
            preStm.setDouble(6, entity.getMeanPrice());
            preStm.setInt(7, entity.getDistrictId());
            preStm.setInt(8, entity.getRoom());
            preStm.setInt(9, entity.getRestRoom());
            preStm.setString(10, entity.getAddress());
            preStm.setInt(11, entity.getArea());
            preStm.setDouble(12, entity.getLongitude());
            preStm.setDouble(13, entity.getLatitude());

            preStm.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            //throwables.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public static int getCurrentId() {
        int result = 0;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "select IFNULL(max(idApartment),0) as maxId from Apartment";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    result = rs.getInt("maxId");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    //select count(1) as count from Apartment where webUrl = ?

    public static boolean isExisted(String url) {
        int result = 0;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "select count(1) as countD from Apartment where webUrl = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, url);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    result = rs.getInt("countD");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return (result > 0);
    }

    public List<Apartment> getAll() {
        List<Apartment> result = new ArrayList<>();
        Apartment entity;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "Select idApartment, nameApartment, imgUrl, webUrl, price, meanPrice, districtId, room, restRoom, address, area, longitude, latitude from Apartment";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    entity = new Apartment();
                    entity.setId(rs.getInt("idApartment"));
                    entity.setName(rs.getString("nameApartment"));
                    entity.setImgUrl(rs.getString("imgUrl"));
                    entity.setWebUrl(rs.getString("webUrl"));
                    entity.setPrice(rs.getDouble("price"));
                    entity.setMeanPrice(rs.getDouble("meanPrice"));
                    entity.setDistrictId(rs.getInt("districtId"));
                    entity.setRoom(rs.getInt("room"));
                    entity.setRestRoom(rs.getInt("restRoom"));
                    entity.setAddress(rs.getString("address"));
                    entity.setArea(rs.getInt("area"));
                    entity.setLongitude(rs.getDouble("longitude"));
                    entity.setLatitude(rs.getDouble("latitude"));

                    result.add(entity);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return result;
    }

    public Apartment getDetail(int id) {
        Apartment entity = null;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "Select idApartment, nameApartment, imgUrl, webUrl, price, meanPrice, districtId, room, restRoom, address, area, longitude, latitude from Apartment where idApartment = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, id);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    entity = new Apartment();
                    entity.setId(rs.getInt("idApartment"));
                    entity.setName(rs.getString("nameApartment"));
                    entity.setImgUrl(rs.getString("imgUrl"));
                    entity.setWebUrl(rs.getString("webUrl"));
                    entity.setPrice(rs.getDouble("price"));
                    entity.setMeanPrice(rs.getDouble("meanPrice"));
                    entity.setDistrictId(rs.getInt("districtId"));
                    entity.setRoom(rs.getInt("room"));
                    entity.setRestRoom(rs.getInt("restRoom"));
                    entity.setAddress(rs.getString("address"));
                    entity.setArea(rs.getInt("area"));
                    entity.setLongitude(rs.getDouble("longitude"));
                    entity.setLatitude(rs.getDouble("latitude"));
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return entity;
    }

    public static List<Apartment> getAllinDistrict(int id) {
        List<Apartment> result = new ArrayList<>();
        Apartment entity;
        try {
            conn = MyConnection.getMyConnection();
            if (conn != null) {
                String sql = "Select idApartment, nameApartment, imgUrl, webUrl, price, meanPrice, districtId, room, restRoom, address, area, longitude, latitude from Apartment where districtId = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, id);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    entity = new Apartment();
                    entity.setId(rs.getInt("idApartment"));
                    entity.setName(rs.getString("nameApartment"));
                    entity.setImgUrl(rs.getString("imgUrl"));
                    entity.setWebUrl(rs.getString("webUrl"));
                    entity.setPrice(rs.getDouble("price"));
                    entity.setMeanPrice(rs.getDouble("meanPrice"));
                    entity.setDistrictId(rs.getInt("districtId"));
                    entity.setRoom(rs.getInt("room"));
                    entity.setRestRoom(rs.getInt("restRoom"));
                    entity.setAddress(rs.getString("address"));
                    entity.setArea(rs.getInt("area"));
                    entity.setLongitude(rs.getDouble("longitude"));
                    entity.setLatitude(rs.getDouble("latitude"));

                    result.add(entity);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return result;
    }
}
