package com.example.shop;

public class SellerHelper {

    String shopName;
    String shopNum;
    String category;
    String latitude;
    String longitude;

    public SellerHelper() {
    }

    public SellerHelper(String shopName, String shopNum, String category, String latitude, String longitude, String imglink) {
        this.shopName = shopName;
        this.shopNum = shopNum;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imglink = imglink;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    String imglink;
}
