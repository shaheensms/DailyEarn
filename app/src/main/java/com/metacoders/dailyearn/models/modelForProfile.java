package com.metacoders.dailyearn.models;

public class modelForProfile {
    String uid , username , affiliationOf ,headLead ,owngencount , my_AffiliationId  ,  password;

    public modelForProfile() {
    }


    public modelForProfile(String uid, String username, String affiliationOf, String headLead, String owngencount, String my_AffiliationId, String password) {
        this.uid = uid;
        this.username = username;
        this.affiliationOf = affiliationOf;
        this.headLead = headLead;
        this.owngencount = owngencount;
        this.my_AffiliationId = my_AffiliationId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public modelForProfile(String uid, String username, String affiliationOf, String headLead, String owngencount, String my_AffiliationId) {
        this.uid = uid;
        this.username = username;
        this.affiliationOf = affiliationOf;
        this.headLead = headLead;
        this.owngencount = owngencount;
        this.my_AffiliationId = my_AffiliationId;
    }

    public String getMy_AffiliationId() {
        return my_AffiliationId;
    }

    public void setMy_AffiliationId(String my_AffiliationId) {
        this.my_AffiliationId = my_AffiliationId;
    }

    public String getOwngencount() {
        return owngencount;
    }

    public void setOwngencount(String owngencount) {
        this.owngencount = owngencount;
    }

    public String getHeadLead() {
        return headLead;
    }

    public void setHeadLead(String headLead) {
        this.headLead = headLead;
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
