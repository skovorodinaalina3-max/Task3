package com.skovorodina.task3.entity;

import com.skovorodina.task3.resource.Restaurant;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Visitor implements Callable<Boolean> {
    private static final Logger logger = LogManager.getLogger();

    private final int id;
    private final String name;
    private final int dishes;

    public Visitor(int id, String name, int dishes) {
        this.id = id;
        this.name = name;
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDishes() {
        return dishes;
    }

    @Override
    public Boolean call() throws Exception {

        Restaurant restaurant = Restaurant.getInstance();

        boolean gotTable = false;
        for (int attempt = 0; attempt < 3; attempt++) {
            if (restaurant.tryGetTable()) {
                gotTable = true;

                logger.info("{} (ID: {}) got table, wants {} dishes", name, id, dishes);
                break;
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }

        if (!gotTable) {
            logger.info("{} left - no table available", name);
            return false;
        }

        try {

            if (restaurant.tryGetFood(dishes)) {
                logger.info("{} eating {} dishes", name, dishes);
                TimeUnit.MILLISECONDS.sleep(200); // время на еду
                logger.info("{} left after eating", name);
                return true;
            } else {
                logger.info("{} left - not enough food", name);
                return false;
            }
        } finally {

            restaurant.freeTable();
        }
    }
}