package com.example.rest_test.entity;

import javax.persistence.*;

@Entity
@Table(name = "PROPERTIES")
public class Properties {
    @Id
    @Column(name = "PROP_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long prop_id;

    @Column(name = "TYPE")
    private String type;
    @Column(name = "ACT_ID")
    private String actId;


    @Column(name = "LOCATION_X")
    private Long locationX;

    @Column(name = "LOCATION_Y")
    private Long locationY;
    @Column(name = "VIEWED_ID")
    private String viewedId;
    @Column(name = "PAGE_FROM")
    private String pageFrom;
    @Column(name = "PAGE_TO")
    private String pageTo;

    public Long getLocationX() {
        return locationX;
    }

    public void setLocationX(long locationX) {
        this.locationX = locationX;
    }

    public Long getLocationY() {
        return locationY;
    }

    public void setLocationY(long locationY) {
        this.locationY = locationY;
    }

    public String getviewedId() {
        return viewedId;
    }

    public void setviewedId(String viewedId) {
        this.viewedId = viewedId == null ? "null": viewedId;
    }

    public String getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(String pageFrom) {
        this.pageFrom = pageFrom == null ? "null": pageFrom;
    }

    public String getPageTo() {
        return pageTo;
    }

    public void setPageTo(String pageTo) {
        this.pageTo = pageTo == null ? "null": pageTo;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Long getProp_id() {
        return prop_id;
    }

    public void setProp_id(long prop_id) {
        this.prop_id = prop_id;
    }

    public Properties() {
    }

    public Properties(String type, String actId, Long locationX, Long locationY, String viewedId, String pageFrom, String pageTo) {

        this.type = type;
        this.actId = actId;
        this.locationX = locationX;
        this.locationY = locationY;
        this.viewedId = viewedId;
        this.pageFrom = pageFrom;
        this.pageTo = pageTo;
    }
}
