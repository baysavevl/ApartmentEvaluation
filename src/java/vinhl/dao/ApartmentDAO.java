package vinhl.dao;

import vinhl.connection.MyConnection;
import vinhl.jaxb.Apartment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            e.printStackTrace();
        }
    }

    public static void saveApartment(Apartment entity) {
        String sql = "insert into Apartment (idApartment, nameApartment, imgUrl, webUrl, price, meanPrice, districtId, room, restRoom, address, area) values (?,?,?,?,?,?,?,?,?,?,?);";
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
            preStm.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
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
}
