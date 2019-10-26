package com.metacoders.dailyearn.models;

public class modelForProfile {
    String uid , username , affiliationOf ;

    public modelForProfile() {
    }

    public modelForProfile(String uid, String username, String affiliationOf) {
        this.uid = uid;
        this.username = username;
        this.affiliationOf = affiliationOf;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAffiliationOf() {
        return affiliationOf;
    }

    public void setAffiliationOf(String affiliationOf) {
        this.affiliationOf = affiliationOf;
    }
}
