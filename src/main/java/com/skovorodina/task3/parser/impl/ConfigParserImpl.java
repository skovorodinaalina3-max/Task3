package com.skovorodina.task3.parser.impl;

import com.skovorodina.task3.parser.ConfigParser;
import com.skovorodina.task3.exception.RestaurantException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigParserImpl implements ConfigParser{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<String> parseConfig(List<String> lines) throws RestaurantException {
        try {
            if (lines.size() < 3) {
                throw new RestaurantException("Need 3 lines, got: " + lines.size());
            }

            return List.of(
                    lines.get(0).strip(),
                    lines.get(1).strip(),
                    lines.get(2).strip()
            );

        } catch (Exception e) {
            throw new RestaurantException("Config parsing error", e);
        }
    }
}
