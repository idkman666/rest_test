package com.example.rest_test.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "LOGS")
public class Logs implements Serializable {
    @Id
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Column(name = "USER_ID")
    private String userId;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Logs() {
    }

    public Logs(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }
}
