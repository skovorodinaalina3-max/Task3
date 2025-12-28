package com.skovorodina.task3.service;

public interface TableManager {
    boolean tryAcquireTable();
    void releaseTable();
    int getFreeTablesCount();
}
