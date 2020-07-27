package vinhl.testNDS;

import vinhl.dao.ApartmentDAO;

import java.sql.SQLException;

public class TestGeneral {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int testResult = ApartmentDAO.getCurrentId();
        System.out.println(testResult);
    }
}
