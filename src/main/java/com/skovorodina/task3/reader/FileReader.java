package com.skovorodina.task3.reader;

import com.skovorodina.task3.exception.RestaurantException;
import java.util.List;

public interface FileReader {
    List<String> readAllLines(String filePath) throws RestaurantException;
}
