
package vinhl.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Apartment complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Apartment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="imgUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="webUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="meanPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="districtId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="room" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="restRoom" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="score" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Apartment", propOrder = {
        "id",
        "name",
        "imgUrl",
        "webUrl",
        "price",
        "meanPrice",
        "districtId",
        "room",
        "restRoom",
        "address",
        "area",
        "longitude",
        "latitude",
        "score"
})
public class Apartment implements Comparable<Apartment> {

    protected int id;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String imgUrl;
    @XmlElement(required = true)
    protected String webUrl;
    protected double price;
    protected double meanPrice;
    protected int districtId;
    protected int room;
    protected int restRoom;
    @XmlElement(required = true)
    protected String address;
    protected int area;
    protected double longitude;
    protected double latitude;
    protected float score;

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the imgUrl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * Sets the value of the imgUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setImgUrl(String value) {
        this.imgUrl = value;
    }

    /**
     * Gets the value of the webUrl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * Sets the value of the webUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWebUrl(String value) {
        this.webUrl = value;
    }

    /**
     * Gets the value of the price property.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     */
    public void setPrice(double value) {
        this.price = value;
    }

    /**
     * Gets the value of the meanPrice property.
     */
    public double getMeanPrice() {
        return meanPrice;
    }

    /**
     * Sets the value of the meanPrice property.
     */
    public void setMeanPrice(double value) {
        this.meanPrice = value;
    }

    /**
     * Gets the value of the districtId property.
     */
    public int getDistrictId() {
        return districtId;
    }

    /**
     * Sets the value of the districtId property.
     */
    public void setDistrictId(int value) {
        this.districtId = value;
    }

    /**
     * Gets the value of the room property.
     */
    public int getRoom() {
        return room;
    }

    /**
     * Sets the value of the room property.
     */
    public void setRoom(int value) {
        this.room = value;
    }

    /**
     * Gets the value of the restRoom property.
     */
    public int getRestRoom() {
        return restRoom;
    }

    /**
     * Sets the value of the restRoom property.
     */
    public void setRestRoom(int value) {
        this.restRoom = value;
    }

    /**
     * Gets the value of the address property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the area property.
     */
    public int getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     */
    public void setArea(int value) {
        this.area = value;
    }

    /**
     * Gets the value of the longitude property.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     */
    public void setLongitude(double value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the latitude property.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     */
    public void setLatitude(double value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the score property.
     */
    public float getScore() {
        String strFloat = String.format("%.2f", this.score);
        return Float.parseFloat(strFloat);
    }

    /**
     * Sets the value of the score property.
     */
    public void setScore(float value) {
        this.score = value;
    }

    @Override
    public int compareTo(Apartment apartment) {
        int isEqual = 0;
        if (this.getScore() > apartment.getScore()) isEqual = -1;
        if (this.getScore() < apartment.getScore()) isEqual = 1;
        return isEqual;
    }
}
