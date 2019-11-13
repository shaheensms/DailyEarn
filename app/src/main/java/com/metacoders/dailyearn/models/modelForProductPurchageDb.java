/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.models;

public class modelForProductPurchageDb {
    String id , amount , uid  , productList, date   ;

    public modelForProductPurchageDb() {
    }

    public modelForProductPurchageDb(String id, String amount, String uid, String mail, String date) {
        this.id = id;
        this.amount = amount;
        this.uid = uid;
        this.productList = mail;
        this.date = date;
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

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
