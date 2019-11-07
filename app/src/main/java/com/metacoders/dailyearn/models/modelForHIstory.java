package com.metacoders.dailyearn.models;

public class modelForHIstory {
    String  id , transID , reason , status , date , amount ;

    public modelForHIstory() {
    }

    public modelForHIstory(String id, String transID, String reason, String status, String date, String amount) {
        this.id = id;
        this.transID = transID;
        this.reason = reason;
        this.status = status;
        this.date = date;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
