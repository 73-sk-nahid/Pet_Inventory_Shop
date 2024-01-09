package com.example.pet_inventory;

public class MainModel {
    String name;
    String price;
    String purchase_date;
    String image_url;

    MainModel() {
    }

    public MainModel(String name, String price, String purchase_date, String image_url) {
        this.name = name;
        this.price = price;
        this.purchase_date = purchase_date;
        this.image_url = image_url;
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

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
