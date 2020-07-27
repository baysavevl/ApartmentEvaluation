package vinhl.filter;

public class ApartmentFilter {
    public int id;

    public double minPrice;
    public float maxprice;

    public int minRoom;
    public int maxRoom;

    public int minRestRoom;
    public int maxRestRoom;

    public int minArea;
    public int maxArea;

    public int weightPrice;
    public int weightArea;
    public int weightRoom;
    public int weighRest;

    public ApartmentFilter() {
    }

    public ApartmentFilter(int id, double minPrice, float maxprice, int minRoom, int maxRoom, int minRestRoom, int maxRestRoom, int minArea, int maxArea) {
        this.id = id;
        this.minPrice = minPrice;
        this.maxprice = maxprice;
        this.minRoom = minRoom;
        this.maxRoom = maxRoom;
        this.minRestRoom = minRestRoom;
        this.maxRestRoom = maxRestRoom;
        this.minArea = minArea;
        this.maxArea = maxArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(float maxprice) {
        this.maxprice = maxprice;
    }

    public int getMinRoom() {
        return minRoom;
    }

    public void setMinRoom(int minRoom) {
        this.minRoom = minRoom;
    }

    public int getMaxRoom() {
        return maxRoom;
    }

    public void setMaxRoom(int maxRoom) {
        this.maxRoom = maxRoom;
    }

    public int getMinRestRoom() {
        return minRestRoom;
    }

    public void setMinRestRoom(int minRestRoom) {
        this.minRestRoom = minRestRoom;
    }

    public int getMaxRestRoom() {
        return maxRestRoom;
    }

    public void setMaxRestRoom(int maxRestRoom) {
        this.maxRestRoom = maxRestRoom;
    }

    public int getMinArea() {
        return minArea;
    }

    public void setMinArea(int minArea) {
        this.minArea = minArea;
    }

    public int getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(int maxArea) {
        this.maxArea = maxArea;
    }

    public int getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(int weightPrice) {
        this.weightPrice = weightPrice;
    }

    public int getWeightArea() {
        return weightArea;
    }

    public void setWeightArea(int weightArea) {
        this.weightArea = weightArea;
    }

    public int getWeightRoom() {
        return weightRoom;
    }

    public void setWeightRoom(int weightRoom) {
        this.weightRoom = weightRoom;
    }

    public int getWeighRest() {
        return weighRest;
    }

    public void setWeighRest(int weighRest) {
        this.weighRest = weighRest;
    }
}
