package com.skovorodina.task3.resource;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Restaurant {
    private static final Logger logger = LogManager.getLogger();
    private static Restaurant instance;

    private final Semaphore tables;
    private int food;
    private final ReentrantLock lock = new ReentrantLock();

    private Restaurant(int tableCount, int initialFood) {
        this.tables = new Semaphore(tableCount);
        this.food = initialFood;
    }

    public static Restaurant getInstance(int tableCount, int initialFood) {
        if (instance == null) {
            instance = new Restaurant(tableCount, initialFood);
        }
        return instance;
    }

    public boolean tryGetTable() {
        boolean gotTable = tables.tryAcquire();
        if (gotTable) {
            logger.info("Table taken. Free tables: {}", tables.availablePermits());
        }
        return gotTable;
    }

    public void freeTable() {
        tables.release();
        logger.info("Table freed. Free tables: {}", tables.availablePermits());
    }

    public boolean tryGetFood(int amount) {
        lock.lock();
        try {
            if (food >= amount) {
                food -= amount;
                logger.info("Food taken: {}. Left: {}", amount, food);
                return true;
            }
            logger.info("Not enough food. Need: {}, Have: {}", amount, food);
            return false;
        } finally {
            lock.unlock();
        }
    }

    public int getAvailableTables() {
        return tables.availablePermits();
    }

    public int getAvailableFood() {
        return food;
    }
}