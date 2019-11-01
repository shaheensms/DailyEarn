package com.metacoders.dailyearn.models;

public class modelForPakage {
    String name , id  , price  ;

    public modelForPakage() {
    }

    public modelForPakage(String name, String id, String price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
