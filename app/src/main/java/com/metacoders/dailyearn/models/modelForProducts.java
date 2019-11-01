package com.metacoders.dailyearn.models;

public class modelForProducts {

            String  id , name , price , discount , disc ,  link ;


    public modelForProducts() {
    }

    public modelForProducts(String id, String name, String price, String discount, String disc, String link) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.disc = disc;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }
}
