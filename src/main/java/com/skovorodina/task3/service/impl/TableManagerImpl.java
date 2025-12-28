package com.skovorodina.task3.service.impl;

import com.skovorodina.task3.service.TableManager;
import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableManagerImpl implements TableManager {
    private static final Logger logger = LogManager.getLogger();
    private final Semaphore semaphore;

    public TableManagerImpl(int tableCount) {
        this.semaphore = new Semaphore(tableCount, true);
        logger.info("TableManager: {} tables available", tableCount);
    }

    @Override
    public boolean tryAcquireTable() {
        boolean acquired = semaphore.tryAcquire();
        if (acquired) {
            logger.debug("Table acquired. Free tables: {}", getFreeTablesCount());
        }
        return acquired;
    }

    @Override
    public void releaseTable() {
        semaphore.release();
        logger.debug("Table released. Free tables: {}", getFreeTablesCount());
    }

    @Override
    public int getFreeTablesCount() {
        return semaphore.availablePermits();
    }
}