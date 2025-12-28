package com.skovorodina.task3.service.impl;

import com.skovorodina.task3.service.KitchenService;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KitchenServiceImpl implements KitchenService {
    private static final Logger logger = LogManager.getLogger();
    private final ReentrantLock lock = new ReentrantLock(true);
    private int foodAmount;

    public KitchenServiceImpl(int initialFood) {
        this.foodAmount = initialFood;
        logger.info("KitchenService initialized with {} dishes", initialFood);
    }

    @Override
    public boolean tryTakeFood(int amount) {
        lock.lock();
        try {
            if (foodAmount >= amount) {
                foodAmount -= amount;
                logger.info("Taken {} dishes. Remaining: {}", amount, foodAmount);
                return true;
            }
            logger.debug("Not enough food. Requested: {}, Available: {}", amount, foodAmount);
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void addFood(int amount) {
        lock.lock();
        try {
            foodAmount += amount;
            logger.info("Added {} dishes. Total: {}", amount, foodAmount);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getAvailableFood() {
        lock.lock();
        try {
            return foodAmount;
        } finally {
            lock.unlock();
        }
    }
}