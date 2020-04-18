package com.example.rest_test.business.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

public class Actions_domain {

    private String time;
    private String type;

    private Prop_domain properties;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Prop_domain getProperties() {
        return properties;
    }

    public void setProperties(Prop_domain properties) {
        this.properties = properties;
    }
}
