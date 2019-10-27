package com.metacoders.dailyearn.models;

public class modelForProfile {
    String uid , username , affiliationOf ,headLead ,owngencount  ;

    public modelForProfile() {
    }



    public modelForProfile(String uid, String username, String affiliationOf, String headLead, String owngencount) {
        this.uid = uid;
        this.username = username;
        this.affiliationOf = affiliationOf;
        this.headLead = headLead;
        this.owngencount = owngencount;
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
