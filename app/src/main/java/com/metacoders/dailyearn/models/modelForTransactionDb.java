

package com.metacoders.dailyearn.models;

public class modelForTransactionDb {
    String id , amount , uid  , mail , date , method  ;

    public modelForTransactionDb() {
    }


    public modelForTransactionDb(String id, String amount, String uid, String mail, String date, String method) {
        this.id = id;
        this.amount = amount;
        this.uid = uid;
        this.mail = mail;
        this.date = date;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
