package com.skovorodina.task3.service;

public interface KitchenService {
    boolean tryTakeFood(int amount);
    void addFood(int amount);
    int getAvailableFood();
}
