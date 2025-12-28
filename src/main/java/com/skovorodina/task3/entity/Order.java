package com.skovorodina.task3.entity;

public class Order {
    private final int visitorId;
    private final int dishCount;

    public Order(int visitorId, int dishCount) {
        this.visitorId = visitorId;
        this.dishCount = dishCount;
    }

    public int getVisitorId() { return visitorId; }
    public int getDishCount() { return dishCount; }
}
