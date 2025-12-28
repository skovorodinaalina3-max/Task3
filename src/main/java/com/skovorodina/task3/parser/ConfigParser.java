package com.skovorodina.task3.parser;
import com.skovorodina.task3.exception.RestaurantException;
import java.util.List;

public interface ConfigParser {
    List<String> parseConfig(List<String> lines) throws RestaurantException;
}
