package com.sanjivani.diplomamate.model;

public class HomeSliderModel {

    String imageUrl, homeTitle, homeDescribe, homeUrl;

    public HomeSliderModel() {

    }

    public HomeSliderModel(String imageUrl, String homeTitle, String homeDescribe, String homeUrl) {
        this.imageUrl = imageUrl;
        this.homeTitle = homeTitle;
        this.homeDescribe = homeDescribe;
        this.homeUrl = homeUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHomeTitle() {
        return homeTitle;
    }

    public void setHomeTitle(String homeTitle) {
        this.homeTitle = homeTitle;
    }

    public String getHomeDescribe() {
        return homeDescribe;
    }

    public void setHomeDescribe(String homeDescribe) {
        this.homeDescribe = homeDescribe;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }
}
