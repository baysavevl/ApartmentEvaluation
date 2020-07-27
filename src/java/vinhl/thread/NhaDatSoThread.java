package vinhl.thread;

import vinhl.constant.AppConstant;
import vinhl.crawlers.NhaDatSoDistrictGenerator;
import vinhl.crawlers.NhaDatSoPageCrawler;
import vinhl.dao.DistrictDAO;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaDatSoThread extends BaseThread implements Runnable {
    @Override
    public void run() {
        NhaDatSoDistrictGenerator generator = new NhaDatSoDistrictGenerator();

        String urlDistrict;
        int districtId;

        while (true) {
            try {
                Map<String, String> listDistrict = generator.getDistrict();
                for (Map.Entry<String, String> entry : listDistrict.entrySet()) {
                    urlDistrict = entry.getValue();
                    districtId = DistrictDAO.getDistrictId(entry.getKey());
                    System.out.println("url = " + urlDistrict + " district Id = " + districtId);
                    Thread crawlingDistrict = new Thread(new NhaDatSoPageCrawler(urlDistrict, districtId));
                    crawlingDistrict.start();
                }
                sleep(TimeUnit.DAYS.toMillis(AppConstant.breakTimeCrawling));

                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NhaDatSoThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
