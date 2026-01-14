package com.skovorodina.task3.main;

import com.skovorodina.task3.entity.Visitor;
import com.skovorodina.task3.reader.FileReader;
import com.skovorodina.task3.reader.impl.FileReaderImpl;
import com.skovorodina.task3.resource.Restaurant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> lines = readConfig(reader);

        if (lines == null) return;

        Restaurant restaurant = parseAndInitRestaurant(lines);
        if (restaurant == null) return;

        List<Visitor> visitors = parseVisitors(lines);

        runSimulation(restaurant, visitors);

        logFinalState(restaurant);
    }

    private static List<String> readConfig(FileReader reader) {
        try {
            List<String> lines = reader.readAllLines("data/config.txt");
            if (lines.isEmpty()) {
                logger.error("Empty config");
                return null;
            }
            return lines;
        } catch (Exception e) {
            logger.error("Config file error", e);
            return null;
        }
    }

    private static Restaurant parseAndInitRestaurant(List<String> lines) {
        try {
            String[] configParts = lines.get(0).split(" ");
            int tableCount = Integer.parseInt(configParts[0]);
            int initialFood = Integer.parseInt(configParts[1]);

            Restaurant restaurant = Restaurant.getInstance(tableCount, initialFood);
            logger.info("Restaurant: {} tables, {} food", tableCount, initialFood);
            return restaurant;

        } catch (Exception e) {
            logger.error("Bad config", e);
            return null;
        }
    }

    private static List<Visitor> parseVisitors(List<String> lines) {
        List<Visitor> visitors = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).strip();
            if (line.isEmpty()) continue;

            try {
                String[] parts = line.split(" ");
                int id = i; // ID = номер строки (начиная с 1)
                String name = parts[0];
                int dishes = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;

                visitors.add(new Visitor(id, name, dishes));

            } catch (Exception e) {
                logger.error("Bad visitor line: {}", line);
            }
        }

        return visitors;
    }

    private static void runSimulation(Restaurant restaurant, List<Visitor> visitors) {
        int poolSize = restaurant.getAvailableTables();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        logger.info("Starting simulation with {} visitors (max {} concurrent)",
                visitors.size(), poolSize);

        try {
            List<Future<Boolean>> results = executor.invokeAll(visitors);

            for (Future<Boolean> result : results) {
                try {
                    result.get();
                } catch (Exception e) {
                    logger.error("Visitor error", e);
                }
            }

        } catch (InterruptedException e) {
            logger.error("Interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }

    private static void logFinalState(Restaurant restaurant) {
        logger.info("Simulation done. Tables free: {}, Food left: {}",
                restaurant.getAvailableTables(), restaurant.getAvailableFood());
    }
}