package com.template;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Factory concreto que crea instancias de LinkedHashMap.
 */
public class LinkedHashMapFactory implements MapFactory {

    @Override
    public <K, V> Map<K, V> createMap() {
        return new LinkedHashMap<>();
    }
}
