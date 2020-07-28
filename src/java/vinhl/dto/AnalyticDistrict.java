package vinhl.dto;

public class AnalyticDistrict {

    public String name;
    public int id;
    public int count;
    public double avgePrice;
    public double stdPrice;
    public double avgMean;
    public double stdMean;
    public double avgArea;
    public double minPrice;
    public double maxPrice;
    public int minArea;
    public int maxArea;
    public int minRoom;
    public int maxRoom;
    public int minRest;
    public int maxRest;
    public double rangePriL;
    public double rangePriU;

    public AnalyticDistrict() {
    }

    public AnalyticDistrict(String name, int id, int count, double avgePrice, double stdPrice, double avgMean, double stdMean, double avgArea, double minPrice, double maxPrice, int minArea, int maxArea, int minRoom, int maxRoom, int minRest, int maxRest) {
        this.name = name;
        this.id = id;
        this.count = count;
        this.avgePrice = avgePrice;
        this.stdPrice = stdPrice;
        this.avgMean = avgMean;
        this.stdMean = stdMean;
        this.avgArea = avgArea;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minRoom = minRoom;
        this.maxRoom = maxRoom;
        this.minRest = minRest;
        this.maxRest = maxRest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAvgePrice() {
        String strFloat = String.format("%.1f", this.avgePrice);
        return Double.parseDouble(strFloat);
    }

    public void setAvgePrice(double avgePrice) {
        this.avgePrice = avgePrice;
    }

    public double getStdPrice() {
        return stdPrice;
    }

    public void setStdPrice(double stdPrice) {
        this.stdPrice = stdPrice;
    }

    public double getAvgMean() {
        String strFloat = String.format("%.1f", this.avgMean);
        return Double.parseDouble(strFloat);
    }

    public void setAvgMean(double avgMean) {
        this.avgMean = avgMean;
    }

    public double getStdMean() {
        return stdMean;
    }

    public void setStdMean(double stdMean) {
        this.stdMean = stdMean;
    }

    public double getAvgArea() {
        String strFloat = String.format("%.1f", this.avgArea);
        return Double.parseDouble(strFloat);
    }

    public void setAvgArea(double avgArea) {
        this.avgArea = avgArea;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
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

    public int getMinRest() {
        return minRest;
    }

    public void setMinRest(int minRest) {
        this.minRest = minRest;
    }

    public int getMaxRest() {
        return maxRest;
    }

    public void setMaxRest(int maxRest) {
        this.maxRest = maxRest;
    }

    public double getRangePriL() {
        String strFloat = String.format("%.1f", this.rangePriL);
        return Double.parseDouble(strFloat);
    }

    public void setRangePriL(double rangePriL) {
        this.rangePriL = rangePriL;
    }

    public double getRangePriU() {
        String strFloat = String.format("%.1f", this.rangePriU);
        return Double.parseDouble(strFloat);
    }

    public void setRangePriU(double rangePriU) {
        this.rangePriU = rangePriU;
    }
}
