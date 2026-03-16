package com.template;

import java.util.Map;
import java.util.TreeMap;

/**
 * Factory concreto que crea instancias de TreeMap.
 */
public class TreeMapFactory implements MapFactory {

    @Override
    public <K, V> Map<K, V> createMap() {
        return new TreeMap<>();
    }
}
