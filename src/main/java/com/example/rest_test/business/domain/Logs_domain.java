package com.example.rest_test.business.domain;

import java.util.List;

public class Logs_domain {
    private String userId;
    private String sessionId;
    private List<Actions_domain> actions;

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

    public List<Actions_domain> getActions() {
        return actions;
    }

    public void setActions(List<Actions_domain> actions) {
        this.actions = actions;
    }
}
