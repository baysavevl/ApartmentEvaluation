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

import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaDatNhanhApartmentDetailCrawler extends BaseCrawler {

    private String url;

    public NhaDatNhanhApartmentDetailCrawler(String url) {
        this.url = url;
    }

    public NhaDatNhanhApartmentDetailCrawler() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NhaDatNhanhApartmentDetailCrawler(ServletContext context) {
        super(context);
    }


    public void getApartmentDetail(String url, int idDistrict) throws Exception {
        BufferedReader reader = null;
        XMLChecker XMLChecker = new XMLChecker();
        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isEnd = false;
            boolean isFound = false;

            boolean isFoundName = false;
            boolean isFoundPrice = false;
            boolean isFoundPicture = false;
            boolean isFoundAddress = false;
            boolean isFoundRoom = false;
            boolean isFoundRest = false;

            int countRoom = 0;
            int countRest = 0;

            while ((line = reader.readLine()) != null) {
                if (isFound && line.contains("div id=\"Description\"")) {
                    isEnd = true;
                }

                //Name
                if (line.contains("row-fluid")) {
                    isFound = true;
                    document += line.trim();
                    isFoundName = true;
                }

                if (isFoundName && line.contains("</h1>")) {
                }

                //Price
                if (isFound && !isFoundPrice && line.contains("span class=\"price\"")) {
                    isFoundPrice = true;
                }

                if (isFoundPrice && line.contains("</span>")) {
                    document += line.trim();
                    isFoundPrice = false;
                }

                //Address
                if (isFound && !isFoundAddress && line.contains("col-sm-9 col-xs-12 padding-top-custom-devive address-advert")) {
                    isFoundAddress = true;
                }

                if (isFoundAddress && line.contains("</div>")) {
                    document += line.trim();
                    isFoundAddress = false;
                }


                //Picture Link
                if (isFound && !isFoundPicture && line.contains("tvt_elt_1")) {
                    document += line.trim();
                    isFoundPicture = true;
                }


                //Room - Rest

                if (isFound && !isFoundRoom && line.contains("Số phòng ngủ")) {
                    isFoundRoom = true;
                }

                if (isFoundRoom && line.contains("</td>")) {
                    countRoom++;
                }

                if (countRoom == 2) {
                    document += line.trim();
                    isFoundRoom = false;
                    countRoom = 0;
                }

                if (isFound && !isFoundRest && line.contains("Số phòng tắm")) {
                    isFoundRest = true;
                }

                if (isFoundRest && line.contains("</td>")) {
                    countRest++;
                }

                if (countRest == 2) {
                    document += line.trim();
                    isFoundRest = false;
                    countRest = 0;
                }

                if (isFoundPrice || isFoundRoom || isFoundRest || isFoundAddress) {
                    document += line.trim();
                }

                if (isEnd) {
                    break;
                }
            }
            if (isFound) {
                document = "<div>" + document + "</div>";
            }
            //System.out.println(XMLChecker.refineHtml(document));
            stAXParserForApartmentDetail(XMLChecker.refineHtml(document), url, idDistrict);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(DiaOcDistrictCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //return null;
    }

    public void stAXParserForApartmentDetail(String document, String url, int idDistrict) throws Exception {
        document = document.trim();
        if (document.isEmpty()) {
            return;
        }
        XMLEventReader eventReader = parseStringToXMLEventReader(document);

        double price = 0.0;
        String strAddress = "";
        String apartmentName = "";
        String imgUrl = "";
        String strArea = "";
        int area = -1;
        int room = -1;
        int resRoom = -1;
        double meanPrice = 0;
        Double[] location;
        double longitude;
        double latitude;

        String moneyPrefix = "";
        String moneySuffix = "";

        String className = "";
        String tagName = "";
        String subTagName = "";
        Characters character;
        Attribute attribute;


        while (eventReader.hasNext()) {

            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("h1".equals(tagName)) {
                    //Name
                    attribute = startElement.getAttributeByName(new QName("class"));
                    if (attribute.getValue().equalsIgnoreCase("row-fluid")) {
                        event = (XMLEvent) eventReader.nextEvent();
                        character = event.asCharacters();
                        apartmentName = character.getData();
                        if (StringUtils.isNullOrEmpty(apartmentName)) {
                            return;
                        }
                    }
                } else if ("span".equals(tagName)) {
                    //Price
                    if (startElement.getAttributeByName(new QName("class")) != null) {
                        attribute = startElement.getAttributeByName(new QName("class"));
                        className = attribute.getValue();
                        if (className.equalsIgnoreCase("price")) {
                            String strPrice = eventReader.next().toString();

                            //PROCESS PRICE
                            String[] array = strPrice.split("-");
                            //Money
                            moneyPrefix = array[0];
                            String[] moneyArray = moneyPrefix.split(" ");
                            moneyPrefix = moneyArray[0];
                            moneySuffix = moneyArray[1];

                            if (!NumberHelper.isNumeric(moneyPrefix)) {
                                return;
                            }
                            if (moneySuffix.contains(Constants.MONEY_BILLION)) {
                                moneySuffix = Constants.MONEY_BILLION;
                            }
                            if (moneySuffix.equalsIgnoreCase(Constants.MONEY_MILLION) && NumberHelper.extractNumber(moneyPrefix) < WebsiteConstant.NhaDatNhanh.minPrice) {
                                return;
                            } else {
                                price = NumberHelper.decodeMoney(moneyPrefix, moneySuffix);
                            }

                            if (price == 0) {
                                return;
                            }

                            //Area
                            strArea = array[1];
                            if (!NumberHelper.containsNumber(strArea)) {
                                return;
                            }
                            area = NumberHelper.extractNumber(strArea);
                            if (area > 750) {
                                return;
                            }
                            if (area < 1) {
                                return;
                            }
                        }
                    }
                } else if ("div".equals(tagName)) {
                    if (startElement.getAttributeByName(new QName("class")) != null) {
                        boolean isStop = false;
                        while (!isStop) {
                            subTagName = eventReader.next().toString();
                            if (subTagName.contains("<a")) {
                                strAddress += eventReader.next().toString() + ", ";
                            } else if (subTagName.equalsIgnoreCase("</div>")) {
                                isStop = true;
                            }
                        }
                        strAddress = strAddress.substring(0, strAddress.length() - 2);
                        if (StringUtils.isNullOrEmpty(strAddress)) {
                            return;
                        }
                    }
                } else if ("img".equals(tagName)) {
                    attribute = startElement.getAttributeByName(new QName("src"));
                    imgUrl = WebsiteConstant.NhaDatNhanh.prefixPage + attribute.getValue();
                    if (StringUtils.isNullOrEmpty(imgUrl)) {
                        return;
                    }
                } else if ("td".equals(tagName)) {
                    eventReader.next();
                    eventReader.next();
                    eventReader.next();
                    String roomStr = eventReader.next().toString();
                    if (NumberHelper.isNumeric(roomStr)) {
                        room = NumberHelper.extractNumber(roomStr);
                        eventReader.next();
                    }
                    if (room < 1 || room > Constants.MAX_ROOM) return;
                    eventReader.next();
                    eventReader.next();
                    eventReader.next();
                    eventReader.next();
                    String restStr = eventReader.next().toString();
                    if (NumberHelper.isNumeric(restStr)) {
                        resRoom = NumberHelper.extractNumber(restStr);
                    }
                    if (resRoom < 1 || resRoom > Constants.MAX_ROOM) return;
                }
            }
        }

        meanPrice = price / area;
        price = (price / Constants.DIV_MILLION);
        location = GeoCoding.getLocation(strAddress);
        latitude = location[0];
        longitude = location[1];

        Apartment apartment = new Apartment();
        apartment.setName(apartmentName);
        apartment.setImgUrl(imgUrl);
        apartment.setWebUrl(url);
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


        System.out.println("apartName = " + apartmentName);
        System.out.println("link = " + url);
        System.out.println("price = " + price);
        System.out.println("Area = " + area);
        System.out.println("address = " + strAddress);
        System.out.println("districtId = " + idDistrict);
        System.out.println("Image Link = " + imgUrl);
        System.out.println("room = " + room);
        System.out.println("rest = " + resRoom);
        System.out.println("Mean = " + meanPrice);
        System.out.println("Lat = " + latitude);
        System.out.println("Long = " + longitude);

        System.out.println(Constants.ID_APARTMENT++);
        System.out.println("--------------");


        try {
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(NhaDatNhanhApartmentDetailCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


