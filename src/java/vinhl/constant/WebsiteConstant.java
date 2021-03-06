package vinhl.constant;

public class WebsiteConstant {
    public static class DiaOc {
        public static final String urlDiaOcHomePage = "https://diaoconline.vn/sieu-thi/loc/?tindang=1&diaoc=8&tp=3";
        public static final String prefixPage = "https://diaoconline.vn/";
        public static final String prefixDistrict = "https://diaoconline.vn/sieu-thi/loc/?tp=3&qh=";
        public static final String suffixDistrict = "&tindang=1&diaoc=8";

        public static final String exceptDistrictTag = "Quận/Huyện";
    }

    public static class NhaDatSo {
        public static final String urlNhaDatSoHomePage = "https://nhadatso.com/ban-can-ho-chung-cu-tai-ho-chi-minh/";
        public static final String prefixPage = "https://nhadatso.com/";
        public static final String prefixDistrict = "nha-dat-ban-can-ho-chung-cu-tai-";
        public static final String suffixDistrict = "-ho-chi-minh/";

        public static final String error = "Có lỗi xảy ra";
        public static final String paging = "?p=";

        public static final String classPrice = "fa fa-dollar-sign text-center";
        public static final String classAddress = "fa fa-map-marker text-center";
        public static final String classArea = "fa fa-home pr-1";
        public static final String classRoom = "fa fa-bed pr-1";
        public static final String classRest = "fa fa-male pr-1";

        public static final String removeDiaChi = "Địa chỉ: ";
    }

    public static class NhaDatNhanh {
        public static final String pageSite = "";
        public static final String mainPage = "https://nhadatnhanh.vn/can-ho-ban-tpho-chi-minh";
        public static final String prefixPage ="https://nhadatnhanh.vn";
        public static final String paging = "/page:";

        public static final String tagRoom = "Số phòng ngủ";
        public static final String tagRest = "Số phòng tắm";

        public static final int minPrice = 300;
    }

    public static class District {
        public static final String homePage = "http://pso.hochiminhcity.gov.vn/web/guest/danhmucthongke-danhmucquanhuyen";
    }
}
