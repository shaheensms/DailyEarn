package com.metacoders.dailyearn.models;

public class modelForafflitaion {


    private String uid  , genCount , head, genLvl ;

    public modelForafflitaion() {
    }

    public modelForafflitaion(String uid, String genCount, String headLead, String genLvl) {
        this.uid = uid;
        this.genCount = genCount;
        this.head = headLead;
        this.genLvl = genLvl;
    }

    public String getGenLvl() {
        return genLvl;
    }

    public void setGenLvl(String genLvl) {
        this.genLvl = genLvl;
    }

    public modelForafflitaion(String uid, String genCount, String headLead) {
        this.uid = uid;
        this.genCount = genCount;
        this.head = headLead;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getGenCount() {
        return genCount;
    }

    public void setGenCount(String genCount) {
        this.genCount = genCount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
