package com.template;

import java.io.IOException;
import java.util.*;

public class PruebaRendimiento {

    private static final String RUTA_INVENTARIO = "src/inventario.txt";
    private static final int NUMERO_OPERACIONES = 1000;

    public static void main(String[] args) {
        System.out.println("PRUEBA DE RENDIMIENTO");
        try {
            Inventario inventario = new Inventario(RUTA_INVENTARIO);
            System.out.println("Inventario cargado con " + inventario.getCatalogoCompleto().size() + " productos\n");
            probarImplementacion(1, "HashMap", inventario);
            probarImplementacion(2, "TreeMap", inventario);
            probarImplementacion(3, "LinkedHashMap", inventario);

        } catch (IOException e) {
            System.out.println("Error al cargar el inventario: " + e.getMessage());
        }
    }

    private static void probarImplementacion(int opcionMap, String nombreImplementacion, Inventario inventario) {
        System.out.println("Probando implementación: " + nombreImplementacion);

        MapFactory factory = MapFactoryProducer.getFactory(opcionMap);
        ColeccionUsuario coleccion = new ColeccionUsuario(factory);

        poblarColeccion(coleccion, inventario);

        long tiempoInicio = System.nanoTime();

        Map<String, String[]> coleccionMap = coleccion.getColeccion();
        for (int i = 0; i < NUMERO_OPERACIONES; i++) {
            for (Map.Entry<String, String[]> entry : coleccionMap.entrySet()) {
                String producto = entry.getKey();
                String categoria = entry.getValue()[0];
                String cantidad = entry.getValue()[1];
            }
        }

        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;
        double tiempoPromedio = (double) tiempoTotal / NUMERO_OPERACIONES;

        System.out.println("Tiempo total para " + NUMERO_OPERACIONES + " operaciones: " + 
                          formatoTiempo(tiempoTotal));
        System.out.println("Tiempo promedio por operación: " + 
                          formatoTiempo(tiempoPromedio));
        System.out.println();
    }

    private static void poblarColeccion(ColeccionUsuario coleccion, Inventario inventario) {
        List<String> productos = new ArrayList<>(inventario.getProductosDisponibles());
        
        int limite = Math.min(10, productos.size());
        for (int i = 0; i < limite; i++) {
            String producto = productos.get(i);
            String categoria = inventario.getCategoria(producto);
            coleccion.agregarProducto(producto, categoria);
            
            if (i % 3 == 0) {
                coleccion.agregarProducto(producto, categoria);
            }
        }
        
        System.out.println("  Colección poblada con " + coleccion.getColeccion().size() + 
                          " productos únicos (con cantidades)");
    }

    private static String formatoTiempo(double nanosegundos) {
        if (nanosegundos < 1000) {
            return String.format("%.2f ns", nanosegundos);
        } else if (nanosegundos < 1_000_000) {
            return String.format("%.2f µs", nanosegundos / 1000);
        } else if (nanosegundos < 1_000_000_000) {
            return String.format("%.2f ms", nanosegundos / 1_000_000);
        } else {
            return String.format("%.2f s", nanosegundos / 1_000_000_000);
        }
    }
}