package com.example.rest_test.business.domain;

import javax.persistence.Column;

public class Prop_domain {

    private long locationY;
    private long locationX;
    private String pageFrom;
    private String pageTo;
    private String viewedId;


    public long getLocationY() {
        return locationY;
    }

    public void setLocationY(long locationY) {
        this.locationY = locationY;
    }

    public long getLocationX() {
        return locationX;
    }

    public void setLocationX(long locationX) {
        this.locationX = locationX;
    }

    public String getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(String pageFrom) {
        this.pageFrom = pageFrom;
    }

    public String getPageTo() {
        return pageTo;
    }

    public void setPageTo(String pageTo) {
        this.pageTo = pageTo;
    }

    public String getviewedId() {
        return viewedId;
    }

    public void setviewedId(String viewedId) {
        this.viewedId = viewedId;
    }
}
