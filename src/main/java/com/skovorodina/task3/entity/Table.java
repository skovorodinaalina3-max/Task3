package com.skovorodina.task3.entity;

public class Table {
    private final int id;
    private boolean occupied;
    private Visitor currentVisitor;

    public Table(int id) {
        this.id = id;
        this.occupied = false;
        this.currentVisitor = null;
    }

    public int getId() { return id; }
    public boolean isOccupied() { return occupied; }
    public Visitor getCurrentVisitor() { return currentVisitor; }

    public void occupy(Visitor visitor) {
        this.occupied = true;
        this.currentVisitor = visitor;
    }

    public void release() {
        this.occupied = false;
        this.currentVisitor = null;
    }
}
