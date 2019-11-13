/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.models;



public class modelForAdminPaymentDevice {
    String perfectMoney , netTeller , skrill , localBank , bitCoin , coinBase , bkash, rocket , nagad , paytm ;

    public modelForAdminPaymentDevice() {
    }

    public modelForAdminPaymentDevice(String perfectMoney, String netTeller, String skrill, String localBank, String bitCoin, String coinBase, String bkash, String rocket, String nagad, String paytm) {
        this.perfectMoney = perfectMoney;
        this.netTeller = netTeller;
        this.skrill = skrill;
        this.localBank = localBank;
        this.bitCoin = bitCoin;
        this.coinBase = coinBase;
        this.bkash = bkash;
        this.rocket = rocket;
        this.nagad = nagad;
        this.paytm = paytm;
    }

    public String getPerfectMoney() {
        return perfectMoney;
    }

    public void setPerfectMoney(String perfectMoney) {
        this.perfectMoney = perfectMoney;
    }

    public String getNetTeller() {
        return netTeller;
    }

    public void setNetTeller(String netTeller) {
        this.netTeller = netTeller;
    }

    public String getSkrill() {
        return skrill;
    }

    public void setSkrill(String skrill) {
        this.skrill = skrill;
    }

    public String getLocalBank() {
        return localBank;
    }

    public void setLocalBank(String localBank) {
        this.localBank = localBank;
    }

    public String getBitCoin() {
        return bitCoin;
    }

    public void setBitCoin(String bitCoin) {
        this.bitCoin = bitCoin;
    }

    public String getCoinBase() {
        return coinBase;
    }

    public void setCoinBase(String coinBase) {
        this.coinBase = coinBase;
    }

    public String getBkash() {
        return bkash;
    }

    public void setBkash(String bkash) {
        this.bkash = bkash;
    }

    public String getRocket() {
        return rocket;
    }

    public void setRocket(String rocket) {
        this.rocket = rocket;
    }

    public String getNagad() {
        return nagad;
    }

    public void setNagad(String nagad) {
        this.nagad = nagad;
    }

    public String getPaytm() {
        return paytm;
    }

    public void setPaytm(String paytm) {
        this.paytm = paytm;
    }
}
