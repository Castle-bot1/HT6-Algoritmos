package com.template;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory concreto que crea instancias de HashMap.
 */
public class HashMapFactory implements MapFactory {

    @Override
    public <K, V> Map<K, V> createMap() {
        return new HashMap<>();
    }
}
