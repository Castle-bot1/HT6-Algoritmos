package com.template;

import java.util.Map;
import java.util.TreeMap;

/**
 * Representa la colección personal del usuario.
 * Usa el Map creado por el Factory elegido.
 * Clave: nombre del producto
 * Valor: arreglo de dos elementos -> [categoría, cantidad]
 */
public class ColeccionUsuario {

    // Map elegido en tiempo de corrida via Factory
    // Clave: producto  |  Valor: [categoria, cantidad_como_string]
    private final Map<String, String[]> coleccion;

    public ColeccionUsuario(MapFactory factory) {
        this.coleccion = factory.createMap();
    }

    // ---------------------------------------------------------------
    // Operaciones
    // ---------------------------------------------------------------

    /**
     * Agrega un producto a la colección.
     * Si ya existe, incrementa su cantidad.
     *
     * @param producto  nombre del producto
     * @param categoria categoría del producto (tomada del inventario)
     */
    public void agregarProducto(String producto, String categoria) {
        if (coleccion.containsKey(producto)) {
            String[] datos = coleccion.get(producto);
            int cantidad = Integer.parseInt(datos[1]) + 1;
            datos[1] = String.valueOf(cantidad);
        } else {
            coleccion.put(producto, new String[]{categoria, "1"});
        }
    }

    /**
     * Retorna la categoría de un producto de la colección del usuario.
     * @return categoría, o null si el producto no está en la colección.
     */
    public String getCategoriaDeColeccion(String producto) {
        String[] datos = coleccion.get(producto);
        return (datos != null) ? datos[0] : null;
    }

    /**
     * Indica si la colección está vacía.
     */
    public boolean estaVacia() {
        return coleccion.isEmpty();
    }

    /**
     * Retorna el mapa de la colección para recorrerlo.
     * Clave: producto  |  Valor: [categoria, cantidad]
     */
    public Map<String, String[]> getColeccion() {
        return coleccion;
    }

    /**
     * Retorna la colección ordenada por categoría (luego por producto dentro
     * de la misma categoría).
     */
    public Map<String, String[]> getColeccionOrdenadaPorTipo() {
        // TreeMap con comparador personalizado: compara por categoría, luego por producto
        TreeMap<String, String[]> ordenado = new TreeMap<>((a, b) -> {
            String catA = coleccion.get(a)[0];
            String catB = coleccion.get(b)[0];
            int cmp = catA.compareToIgnoreCase(catB);
            if (cmp != 0) return cmp;
            return a.compareToIgnoreCase(b);
        });
        ordenado.putAll(coleccion);
        return ordenado;
    }
}

