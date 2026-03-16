package com.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Carga el archivo inventario.txt y muestra el catálogo completo.
 * El inventario principal siempre usa LinkedHashMap para conservar
 * el orden de inserción del archivo.
 * La clave es el nombre del producto y el valor es la categoría.
 */
public class Inventario {

    // Mapa principal: producto -> categoría  (todo el inventario del archivo)
    private final Map<String, String> catalogoCompleto;

    public Inventario(String rutaArchivo) throws IOException {
        catalogoCompleto = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        cargarArchivo(rutaArchivo);
    }

    // Carga

    private void cargarArchivo(String ruta) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split("\\|", 2);
                if (partes.length == 2) {
                    String categoria = partes[0].trim();
                    String producto  = partes[1].trim();
                    catalogoCompleto.put(producto, categoria);
                }
            }
        }
    }

    // Consultas sobre el catálogo

    /**
     * Verifica si un producto existe en el catálogo (sin importar mayúsculas).
     */
    public boolean existeProducto(String producto) {
        return catalogoCompleto.containsKey(producto);
    }

    /**
     * Retorna la categoría de un producto del catálogo.
     * @return categoría, o null si no existe.
     */
    public String getCategoria(String producto) {
        return catalogoCompleto.get(producto);
    }

    /**
     * Retorna el mapa completo del inventario (producto -> categoría).
     */
    public Map<String, String> getCatalogoCompleto() {
        return catalogoCompleto;
    }

    /**
     * Retorna una lista de todos los productos disponibles.
     */
    public List<String> getProductosDisponibles() {
        return new ArrayList<>(catalogoCompleto.keySet());
    }
}

