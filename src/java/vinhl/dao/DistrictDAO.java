package vinhl.dao;

import vinhl.connection.MyConnection;
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
                preStm.setString(1, "%" + name + "%");
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

}
