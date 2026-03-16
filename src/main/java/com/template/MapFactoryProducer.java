package com.template;

/**
 * Productor del Factory: devuelve el factory correcto
 * según la opción elegida por el usuario en tiempo de corrida.
 */
public class MapFactoryProducer {

    /**
     * Retorna el factory correspondiente a la opción ingresada.
     * @param opcion 1 = HashMap, 2 = TreeMap, 3 = LinkedHashMap
     * @return instancia de MapFactory
     */
    public static MapFactory getFactory(int opcion) {
        switch (opcion) {
            case 1:
                return new HashMapFactory();
            case 2:
                return new TreeMapFactory();
            case 3:
                return new LinkedHashMapFactory();
            default:
                throw new IllegalArgumentException("Opcion invalida. Elija 1, 2 o 3.");
        }
    }
}
