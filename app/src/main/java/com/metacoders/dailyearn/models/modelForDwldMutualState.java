/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.models;

public class modelForDwldMutualState {
    String runningMutualFund , purchangeDate , runningName , price  ;

    public modelForDwldMutualState() {
    }

    public modelForDwldMutualState(String runningMutualFund, String purchangeDate, String runningName, String price) {

        this.runningMutualFund = runningMutualFund;
        this.purchangeDate = purchangeDate;
        this.runningName = runningName;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRunningMutualFund() {
        return runningMutualFund;
    }

    public void setRunningMutualFund(String runningMutualFund) {
        this.runningMutualFund = runningMutualFund;
    }

    public String getPurchangeDate() {
        return purchangeDate;
    }

    public void setPurchangeDate(String purchangeDate) {
        this.purchangeDate = purchangeDate;
    }

    public String getRunningName() {
        return runningName;
    }

    public void setRunningName(String runningName) {
        this.runningName = runningName;
    }
}
