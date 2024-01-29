package com.example.pet_inventory;

import java.util.concurrent.atomic.AtomicInteger;

public class CageModel {
    long id;
    String name;
    long schedule_no;

    CageModel() {
    }

    public CageModel(long id, String name, long schedule_no) {
        this.id = id;
        this.name = name;
        this.schedule_no = schedule_no;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSchedule_no() {
        return schedule_no;
    }

    public void setSchedule_no(long schedule_no) {
        this.schedule_no = schedule_no;
    }
}
