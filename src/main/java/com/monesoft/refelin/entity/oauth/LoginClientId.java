package com.monesoft.refelin.entity.oauth;

public enum LoginClientId {
    GOOGLE("google"), SIMPLE("simple");

    private String clientId;

    LoginClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
