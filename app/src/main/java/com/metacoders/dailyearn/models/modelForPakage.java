package com.metacoders.dailyearn.models;

public class modelForPakage {
    String name , id  , price , detail  , type , image   ;

    public modelForPakage() {
    }

    public modelForPakage(String name, String id, String price, String detail, String type, String image) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.detail = detail;
        this.type = type;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
