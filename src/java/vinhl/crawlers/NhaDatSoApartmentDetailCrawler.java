package vinhl.crawlers;

import com.mysql.cj.util.StringUtils;
import vinhl.constant.Constants;
import vinhl.constant.WebsiteConstant;
import vinhl.dao.ApartmentDAO;
import vinhl.jaxb.Apartment;
import vinhl.thread.BaseThread;
import vinhl.utils.GeoCoding;
import vinhl.utils.NumberHelper;
import vinhl.utils.XMLChecker;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaDatSoApartmentDetailCrawler extends BaseCrawler {

    private String websiteUrl;
    private String imgUrl;
    private int idDistrict;

    public NhaDatSoApartmentDetailCrawler() {
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(int idDistrict) {
        this.idDistrict = idDistrict;
    }


    public void getApartmentDetail(String websiteUrl, String imgUrl, int idDistrict) throws Exception {
        String url = websiteUrl;
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();
        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;
            //col-xs-12 col-sm-12 col-md-12 col-lg-6 pl-2 pr-2 float-left crb_contain_mid contain_news_detail_mid
            boolean isFoundP1 = false;
            //col-12 float-left px-0 d-block d-lg-none post-content-tit//</div>
            boolean isFoundP2 = false;
            //card-text text-list pt-1

            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("</section>")) {
                    isEnd = true;
                }

                //pt-2 title_detail_n
                //name


                if (line.contains("col-xs-12 col-sm-12 col-md-12 col-lg-6 pl-2 pr-2 float-left crb_contain_mid contain_news_detail_mid")) {
                    isFound = true;
                    document += line.trim();
                }

                if (isFound && line.contains("pt-2 title_detail_n")) {
                    document += line.trim();
                    //isFoundP1 = true;
                }

                if (isFound && line.contains("col-12 float-left px-0 d-block d-lg-none post-content-tit")) {
                    document += line.trim();
                    isFoundP1 = true;
                }

                if (isFoundP1) {
                    document += line.trim();
                    if (line.contains("</div>")) {
                        isFoundP1 = false;
                    }
                }

                if (isFound && line.contains("card-text text-list pt-1")) {
                    document += line.trim();
                    isFoundP2 = true;
                }

                if (isFoundP2) {
                    document += line.trim();
                    if (line.contains("</div>")) {
                        isFoundP2 = false;
                    }
                }

                if (isEnd) {
                    break;
                }

            }
            try {
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                //Logger.getLogger(NhaDatSoApartmentDetailCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            stAXParserForApartmentDetail(XMLChecker.refineHtml(document), websiteUrl, imgUrl, idDistrict);
        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException e) {
            //e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                //Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stAXParserForApartmentDetail(String document, String websiteUrl, String imgUrl, int idDistrict) throws Exception {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);

        double price = 0.0;
        double meanPrice;
        String strAddress = "";
        String apartmentName = "";

        int area = -1;
        int room = -1;
        int resRoom = -1;

        String moneyPrefix = "";
        String moneySuffix = "";

        boolean isDiffForm = false;

        String className = "";
        String tagName = "";
        Attribute attribute;

        Double[] location;
        double longitude;
        double latitude;


        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                //System.out.println(tagName);
                if ("h1".equals(tagName)) {
                    apartmentName = eventReader.next().toString();
                    if (StringUtils.isNullOrEmpty(apartmentName)) {
                        return;
                    }
                } else if ("p".equals(tagName)) {
                    //part 1
                    if (startElement.getAttributeByName(new QName("class")) != null) {
                        attribute = startElement.getAttributeByName(new QName("class"));
                        if (attribute.getValue().isEmpty()) {
                            event = (XMLEvent) eventReader.nextTag();
                            startElement = event.asStartElement();
                            tagName = startElement.getName().getLocalPart();
                            if ("i".equals(tagName)) {
                                attribute = startElement.getAttributeByName(new QName("class"));
                                className = attribute.getValue();
                                eventReader.next();
                                if (className.equalsIgnoreCase(WebsiteConstant.NhaDatSo.classPrice)) {
                                    isDiffForm = false;
                                    boolean stop = false;
                                    while (!stop) {
                                        stop = eventReader.next().toString().equalsIgnoreCase("<b>");
                                    }
                                    moneyPrefix = eventReader.next().toString();
                                    eventReader.next();
                                    moneySuffix = eventReader.next().toString();

                                    if (moneySuffix.contains(Constants.MONEY_BILLION)) {
                                        moneySuffix = Constants.MONEY_BILLION;
                                    }
                                    if (moneySuffix.equalsIgnoreCase(Constants.MONEY_MILLION) && NumberHelper.extractNumber(moneyPrefix) < WebsiteConstant.NhaDatNhanh.minPrice) {
                                        return;
                                    } else {
                                        price = NumberHelper.decodeMoney(moneyPrefix, moneySuffix);
                                        //System.out.println("price =" + price);
                                    }
                                    if (price < Constants.MILLION) {
                                        isDiffForm = true;
                                    }
                                }
                                if (className.equalsIgnoreCase(WebsiteConstant.NhaDatSo.classAddress)) {
                                    strAddress = eventReader.next().toString();
                                    //System.out.println(strAddress);
                                }
                            }
                        }
                    }
                } else if ("span".equals(tagName)) {
                    attribute = startElement.getAttributeByName(new QName("class"));
                    className = attribute.getValue();
                    if (className.equalsIgnoreCase(WebsiteConstant.NhaDatSo.classArea)) {
                        eventReader.next();
                        String strArea = eventReader.next().toString();
                        if (!NumberHelper.containsNumber(strArea)) {
                            return;
                        } else area = NumberHelper.extractNumber(strArea);
                        if (area > 750) {
                            return;
                        }
                        if (area < 1) {
                            return;
                        }
                        if (isDiffForm) {
                            price = NumberHelper.extractNumber(strArea) * NumberHelper.decodeMoney(moneyPrefix, moneySuffix);
                            price = price * Constants.MILLION;
                        }
                    }

                    if (className.equalsIgnoreCase(WebsiteConstant.NhaDatSo.classRest)) {
                        eventReader.next();
                        String strRes = eventReader.next().toString();
                        if (!NumberHelper.containsNumber(strRes)) {
                            return;
                        }

                        resRoom = NumberHelper.extractNumber(strRes);
                        if (resRoom < 1 || resRoom > Constants.MAX_ROOM) return;
                    }

                    if (className.equalsIgnoreCase(WebsiteConstant.NhaDatSo.classRoom)) {
                        eventReader.next();
                        String strRoom = eventReader.next().toString();
                        if (!NumberHelper.containsNumber(strRoom)) {
                            return;
                        }
                        room = NumberHelper.extractNumber(strRoom);
                        if (room < 1 || room > Constants.MAX_ROOM) return;
                    }
                }
            }
        }
        if (price == 0 || room < 0 || resRoom < 0) {
            return;
        }

        if (area < 0) return;

        strAddress = strAddress.trim().replaceAll(WebsiteConstant.NhaDatSo.removeDiaChi, "");

        meanPrice = price / area;
        price = (price / Constants.DIV_MILLION);
        location = GeoCoding.getLocation(strAddress);
        latitude = location[0];
        longitude = location[1];


        Apartment apartment = new Apartment();
        apartment.setName(apartmentName);
        apartment.setImgUrl(imgUrl);
        apartment.setWebUrl(websiteUrl);
        apartment.setAddress(strAddress);
        apartment.setArea(area);
        apartment.setPrice(price);
        apartment.setMeanPrice(meanPrice);
        apartment.setRoom(room);
        apartment.setRestRoom(resRoom);
        apartment.setDistrictId(idDistrict);
        apartment.setLatitude(latitude);
        apartment.setLongitude(longitude);

        ApartmentDAO.saveApartment(apartment);


//        System.out.println("apartmentName = " + apartmentName);
//        System.out.println("Link = " + websiteUrl);
//        System.out.println("Price = " + price);
//        System.out.println("Area = " + area);
//        System.out.println("Address = " + strAddress);
//        System.out.println("districtId = " + idDistrict);
//        System.out.println("Image = " + imgUrl);
//        System.out.println("Room = " + room);
//        System.out.println("Rest = " + resRoom);
//        System.out.println("Mean = " + meanPrice);
//        System.out.println(++Constants.ID_APARTMENT);
//        System.out.println("Lat = " + latitude);
//        System.out.println("Long = " + longitude);
//        System.out.println("--------------");

        try {
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (InterruptedException ex) {
            //Logger.getLogger(NhaDatSoApartmentDetailCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

