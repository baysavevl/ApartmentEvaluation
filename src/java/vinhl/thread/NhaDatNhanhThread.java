package vinhl.thread;

import vinhl.constant.AppConstant;
import vinhl.constant.WebsiteConstant;
import vinhl.crawlers.NhaDatNhanhDistrictCrawler;
import vinhl.crawlers.NhaDatNhanhPageCrawler;
import vinhl.dao.DistrictDAO;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaDatNhanhThread extends BaseThread implements Runnable {

    @Override
    public void run() {
        NhaDatNhanhDistrictCrawler cralwerDistrict = new NhaDatNhanhDistrictCrawler();

        String urlDistrict;
        int districtId;

        while (true) {
            try {
                Map<String, String> listDistrict = cralwerDistrict.getDistrict(WebsiteConstant.NhaDatNhanh.mainPage);
                for (Map.Entry<String, String> entry : listDistrict.entrySet()) {
                    urlDistrict = entry.getValue();
                    districtId = DistrictDAO.getDistrictId(entry.getKey());
                    Thread crawlingDistrict = new Thread(new NhaDatNhanhPageCrawler(urlDistrict, districtId));
                    crawlingDistrict.start();
                }
                sleep(TimeUnit.DAYS.toMillis(AppConstant.breakTimeCrawling));

                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NhaDatNhanhThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
}
