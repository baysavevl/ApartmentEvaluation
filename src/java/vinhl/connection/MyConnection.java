package vinhl.connection;

import vinhl.constant.AppConstant;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection implements Serializable {

    public static Connection getMyConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName(AppConstant.Connection.DRIVER);
        conn = DriverManager.getConnection(AppConstant.Connection.URL, AppConstant.Connection.USER, AppConstant.Connection.PASS);
        return conn;


//        try {
//            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
////            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=XML_Project", "sa", "123456");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
