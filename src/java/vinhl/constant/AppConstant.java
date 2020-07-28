package vinhl.constant;

public class AppConstant {
    public static final int breakTimeCrawling = 2;

    public static class Connection {

        public static final String USER = "root";
        public static final String PASS = "A1234567";

        //public static final String HOST_V ="192.168.1.5";
        public static final String HOST_V = "127.0.0.1";

        public static final String PORT = "3306";
        public static final String DB = "ApartmentEvaluation";
        public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
        public static final String URL = "jdbc:mysql://" + HOST_V + ":" + PORT +"/" + DB;
    }
}
