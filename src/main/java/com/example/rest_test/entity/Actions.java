package com.example.rest_test.entity;

import com.fasterxml.jackson.databind.annotation.JsonAppend;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ACTIONS")
public class Actions {

    @Id
    @Column(name = "ACT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String actId;
    @Column(name = "SESSION_ID")
    private String sessionId;

    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "TIME")
    private String time;
    @Column(name = "TYPE")
    private String type;

    public Actions() {
    }

    public Actions(String actId, String sessionId, String userId, String time, String type) {
        this.actId = actId;
        this.sessionId = sessionId;
        this.userId = userId;
        this.time = time;
        this.type = type;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
}
