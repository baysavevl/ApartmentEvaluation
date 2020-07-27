package vinhl.testNDN;

import vinhl.constant.AppConstant;
import vinhl.constant.Constants;
import vinhl.dao.ApartmentDAO;
import vinhl.thread.NhaDatNhanhThread;

import java.time.LocalTime;

public class TestRun {
    public static void main(String[] args) {

        Constants.ID_APARTMENT = ApartmentDAO.getCurrentId();

        NhaDatNhanhThread nhaDatNhanhThread = new NhaDatNhanhThread();
        nhaDatNhanhThread.start();

        LocalTime timeEnd = LocalTime.now();
        //System.out.println(AppConstant.count);
    }
}
