package com.metacoders.dailyearn.models;

public class modelForProfile {
    String uid , username , affiliationOf ,headLead ,owngencount , my_AffiliationId   , name  ,  password
            , country , nid_password  , status ,activatingDate  ,joining_Date , dob , phone  , adress1 , adress2, mail ;

    public modelForProfile() {
    }

    public modelForProfile(String uid, String username, String affiliationOf, String headLead, String owngencount, String my_AffiliationId, String name, String password, String country, String nid_password, String status, String activatingDate,
                           String joining_Date, String dob, String phone, String adress1, String adress2, String mail) {
        this.uid = uid;
        this.username = username;
        this.affiliationOf = affiliationOf;
        this.headLead = headLead;
        this.owngencount = owngencount;
        this.my_AffiliationId = my_AffiliationId;
        this.name = name;
        this.password = password;
        this.country = country;
        this.nid_password = nid_password;
        this.status = status;
        this.activatingDate = activatingDate;
        this.joining_Date = joining_Date;
        this.dob = dob;
        this.phone = phone;
        this.adress1 = adress1;
        this.adress2 = adress2;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getJoining_Date() {
        return joining_Date;
    }

    public void setJoining_Date(String joining_Date) {
        this.joining_Date = joining_Date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNid_password() {
        return nid_password;
    }

    public void setNid_password(String nid_password) {
        this.nid_password = nid_password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivatingDate() {
        return activatingDate;
    }

    public void setActivatingDate(String activatingDate) {
        this.activatingDate = activatingDate;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress1() {
        return adress1;
    }

    public void setAdress1(String adress1) {
        this.adress1 = adress1;
    }

    public String getAdress2() {
        return adress2;
    }

    public void setAdress2(String adress2) {
        this.adress2 = adress2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
