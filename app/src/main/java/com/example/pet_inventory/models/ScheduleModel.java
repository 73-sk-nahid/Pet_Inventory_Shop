package com.example.pet_inventory.models;

public class ScheduleModel {
    String id;
    String name;
    String food_name;
    String interval_time;
    String measurement;

    ScheduleModel(){

    }

    public ScheduleModel(String id, String name, String food_name, String interval_time, String measurement) {
        this.id = id;
        this.name = name;
        this.food_name = food_name;
        this.interval_time = interval_time;
        this.measurement = measurement;
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

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getInterval_time() {
        return interval_time;
    }

    public void setInterval_time(String interval_time) {
        this.interval_time = interval_time;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
