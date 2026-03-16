package com.template;

import java.util.Map;

/**
 * Interface del Factory para crear implementaciones de Map.
 */
public interface MapFactory {
    /**
     * Crea y retorna una nueva instancia de Map según la implementación elegida.
     * @param <K> tipo de la clave
     * @param <V> tipo del valor
     * @return nueva instancia de Map
     */
    <K, V> Map<K, V> createMap();
}
