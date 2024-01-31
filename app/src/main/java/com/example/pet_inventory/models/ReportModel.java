package com.example.pet_inventory.models;

public class ReportModel {
    String id;
    String profit_loss;

    ReportModel() {

    }

    public ReportModel(String id, String profit_loss) {
        this.id = id;
        this.profit_loss = profit_loss;
    }

    public String getId() {
        return id;
    }

    public String getProfit_loss() {
        return profit_loss;
    }
}
