package com.example.shop;

public class  ItemShopHelper {
    public ItemShopHelper(String imageUrl, String title, String description, String original, String offer) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.original = original;
        this.offer = offer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public ItemShopHelper() {
    }

    String imageUrl,title,description,original,offer;
}
