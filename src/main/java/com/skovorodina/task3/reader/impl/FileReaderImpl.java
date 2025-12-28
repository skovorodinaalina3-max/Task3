package com.skovorodina.task3.reader.impl;

import com.skovorodina.task3.reader.FileReader;
import com.skovorodina.task3.exception.RestaurantException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileReaderImpl implements FileReader {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<String> readAllLines(String filePath) throws RestaurantException {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            logger.info("Read {} lines from {}", lines.size(), filePath);
            return lines;

        } catch (Exception e) {
            throw new RestaurantException("Failed to read file: " + filePath, e);
        }
    }
}