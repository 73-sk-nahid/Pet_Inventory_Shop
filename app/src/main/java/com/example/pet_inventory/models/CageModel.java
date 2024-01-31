package com.example.pet_inventory.models;

import java.util.concurrent.atomic.AtomicInteger;

public class CageModel {
    String id;
    String name;
    String cageType;
    String cageSize;
    String schedule_name;

    CageModel() {
    }

    public CageModel(String id, String name, String cageType, String cageSize, String schedule_no) {
        this.id = id;
        this.name = name;
        this.cageType = cageType;
        this.cageSize = cageSize;
        this.schedule_name = schedule_no;
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

    public String getCageType() {
        return cageType;
    }

    public void setCageType(String cageType) {
        this.cageType = cageType;
    }

    public String getCageSize() {
        return cageSize;
    }

    public void setCageSize(String cageSize) {
        this.cageSize = cageSize;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_no) {
        this.schedule_name = schedule_no;
    }
}
