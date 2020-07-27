package vinhl.dao;

import vinhl.connection.MyConnection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO implements Serializable {
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

    public static String checkLogin(String username, String password) {
        String role = "failed";
        try {
            String sql = "Select role From Account Where username = ? and password = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                role = rs.getString("role").trim();
            }
        } catch (Exception e) {
        } finally {
            closeConnection();
        }
        return role;
    }
}
