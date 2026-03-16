package com.template;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Clase principal del programa.
 * Hoja de Trabajo No. 6 – Operaciones con Mapas
 */
public class Main {

    // Ruta del archivo de inventario
    private static final String RUTA_INVENTARIO = "src/inventario.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // ------------------------------------------------------------------
        // 1. Selección de implementación de MAP (Factory Pattern)
        // ------------------------------------------------------------------
        System.out.println("=======================================================");
        System.out.println("       Hoja de Trabajo 6: Operaciones con Mapas   ");
        System.out.println("=======================================================");
        System.out.println("\nSeleccione la implementacion de MAP a utilizar:");
        System.out.println("  1) HashMap");
        System.out.println("  2) TreeMap");
        System.out.println("  3) LinkedHashMap");
        System.out.print("Ingrese opcion (1-3): ");

        int opcionMap = leerEntero(scanner, 1, 3);

        MapFactory factory;
        try {
            factory = MapFactoryProducer.getFactory(opcionMap);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        String nombreImpl = obtenerNombreImplementacion(opcionMap);
        System.out.println("\nImplementacion seleccionada: " + nombreImpl + "\n");

        // ------------------------------------------------------------------
        // 2. Cargar inventario desde archivo
        // ------------------------------------------------------------------
        Inventario inventario;
        try {
            inventario = new Inventario(RUTA_INVENTARIO);
            System.out.println("Inventario cargado correctamente. (" 
                + inventario.getCatalogoCompleto().size() + " productos)\n");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de inventario: " + e.getMessage());
            System.out.println("Asegúrese de que el archivo 'inventario.txt' esté en el directorio de ejecucion.");
            return;
        }

        // ------------------------------------------------------------------
        // 3. Crear colección del usuario con el factory elegido
        // ------------------------------------------------------------------
        ColeccionUsuario coleccion = new ColeccionUsuario(factory);

        // ------------------------------------------------------------------
        // 4. Menú principal
        // ------------------------------------------------------------------
        int opcion = 0;
        do {
            System.out.println("-----------------------------------------------");
            System.out.println("Implementacion activa: " + nombreImpl);
            System.out.println("MENU PRINCIPAL");
            System.out.println("-----------------------------------------------");
            System.out.println("1. Agregar un producto a mi coleccion");
            System.out.println("2. Mostrar la categoria de un producto");
            System.out.println("3. Mostrar mi coleccion (producto, categoria, cantidad)");
            System.out.println("4. Mostrar mi coleccion ordenada por tipo");
            System.out.println("5. Mostrar todo el inventario disponible");
            System.out.println("6. Mostrar todo el inventario ordenado por tipo");
            System.out.println("0. Salir");
            System.out.print("Ingrese opcion: ");

            opcion = leerEntero(scanner, 0, 6);

            switch (opcion) {
                case 1 -> operacion1_agregarProducto(scanner, inventario, coleccion);
                case 2 -> operacion2_mostrarCategoria(scanner, inventario);
                case 3 -> operacion3_mostrarColeccion(coleccion);
                case 4 -> operacion4_mostrarColeccionOrdenada(coleccion);
                case 5 -> operacion5_mostrarInventario(inventario);
                case 6 -> operacion6_mostrarInventarioOrdenado(inventario);
                case 0 -> System.out.println("\n¡Nos vemos, gracias por haber utilizado el sistema!");
            }

        } while (opcion != 0);

        scanner.close();
    }

    // ==================================================================
    // OPERACIONES
    // ==================================================================

    /**
     * Operación 1: Agregar un producto a la colección del usuario.
     * El usuario ingresa el nombre del producto.
     * Si no existe en el inventario se muestra un error.
     */
    private static void operacion1_agregarProducto(Scanner scanner,
                                                    Inventario inventario,
                                                    ColeccionUsuario coleccion) {
        System.out.println("\n-- Agregar producto a mi coleccion --");
        System.out.println("Productos disponibles:");
        for (String p : inventario.getProductosDisponibles()) {
            System.out.println("  - " + p + "  [" + inventario.getCategoria(p) + "]");
        }
        System.out.print("\nIngrese el nombre del producto: ");
        String nombreProducto = scanner.nextLine().trim();

        if (!inventario.existeProducto(nombreProducto)) {
            System.out.println("ERROR: El producto '" + nombreProducto 
                + "' no se encuentra en el inventario disponible.\n");
            return;
        }

        String categoria = inventario.getCategoria(nombreProducto);
        coleccion.agregarProducto(nombreProducto, categoria);
        System.out.println("Producto agregado: " + nombreProducto 
            + " | Categoria: " + categoria + "\n");
    }

    /**
     * Operación 2: Mostrar la categoría de un producto del inventario.
     * El usuario ingresa el nombre del producto.
     */
    private static void operacion2_mostrarCategoria(Scanner scanner, Inventario inventario) {
        System.out.println("\n-- Mostrar categoria de un producto --");
        System.out.print("Ingrese el nombre del producto: ");
        String nombreProducto = scanner.nextLine().trim();

        String categoria = inventario.getCategoria(nombreProducto);
        if (categoria == null) {
            System.out.println("ERROR: El producto '" + nombreProducto 
                + "' no existe en el inventario.\n");
        } else {
            System.out.println("Producto : " + nombreProducto);
            System.out.println("Categoria: " + categoria + "\n");
        }
    }

    /**
     * Operación 3: Mostrar la colección del usuario
     * (producto, categoría, cantidad) sin ordenamiento específico.
     */
    private static void operacion3_mostrarColeccion(ColeccionUsuario coleccion) {
        System.out.println("\n-- Mi coleccion --");
        if (coleccion.estaVacia()) {
            System.out.println("Su coleccion esta vacia.\n");
            return;
        }
        imprimirEncabezadoColeccion();
        for (Map.Entry<String, String[]> entry : coleccion.getColeccion().entrySet()) {
            String producto  = entry.getKey();
            String categoria = entry.getValue()[0];
            String cantidad  = entry.getValue()[1];
            System.out.printf("| %-35s | %-22s | %-8s |%n",
                    producto, categoria, cantidad);
        }
        imprimirLineaSeparadora();
        System.out.println();
    }

    /**
     * Operación 4: Mostrar la colección del usuario ordenada por tipo (categoría).
     */
    private static void operacion4_mostrarColeccionOrdenada(ColeccionUsuario coleccion) {
        System.out.println("\n-- Mi coleccion (ordenada por tipo) --");
        if (coleccion.estaVacia()) {
            System.out.println("Su coleccion esta vacia.\n");
            return;
        }
        imprimirEncabezadoColeccion();
        for (Map.Entry<String, String[]> entry : coleccion.getColeccionOrdenadaPorTipo().entrySet()) {
            String producto  = entry.getKey();
            String categoria = entry.getValue()[0];
            String cantidad  = entry.getValue()[1];
            System.out.printf("| %-35s | %-22s | %-8s |%n",
                    producto, categoria, cantidad);
        }
        imprimirLineaSeparadora();
        System.out.println();
    }

    /**
     * Operación 5: Mostrar todos los productos del inventario con su categoría.
     */
    private static void operacion5_mostrarInventario(Inventario inventario) {
        System.out.println("\n-- Inventario completo --");
        imprimirEncabezadoInventario();
        for (Map.Entry<String, String> entry : inventario.getCatalogoCompleto().entrySet()) {
            System.out.printf("| %-35s | %-22s |%n",
                    entry.getKey(), entry.getValue());
        }
        imprimirLineaSeparadoraInventario();
        System.out.println();
    }

    /**
     * Operación 6: Mostrar todos los productos del inventario ordenados por tipo (categoría).
     */
    private static void operacion6_mostrarInventarioOrdenado(Inventario inventario) {
        System.out.println("\n-- Inventario completo (ordenado por tipo) --");

        // Ordenar por categoría, luego por producto
        TreeMap<String, String> ordenado = new TreeMap<>((a, b) -> {
            String catA = inventario.getCategoria(a);
            String catB = inventario.getCategoria(b);
            int cmp = catA.compareToIgnoreCase(catB);
            if (cmp != 0) return cmp;
            return a.compareToIgnoreCase(b);
        });
        ordenado.putAll(inventario.getCatalogoCompleto());

        imprimirEncabezadoInventario();
        for (Map.Entry<String, String> entry : ordenado.entrySet()) {
            System.out.printf("| %-35s | %-22s |%n",
                    entry.getKey(), entry.getValue());
        }
        imprimirLineaSeparadoraInventario();
        System.out.println();
    }

    // ==================================================================
    // UTILIDADES DE IMPRESIÓN
    // ==================================================================

    private static void imprimirEncabezadoColeccion() {
        imprimirLineaSeparadora();
        System.out.printf("| %-35s | %-22s | %-8s |%n", "Producto", "Categoria", "Cantidad");
        imprimirLineaSeparadora();
    }

    private static void imprimirLineaSeparadora() {
        System.out.println("|-------------------------------------|------------------------|----------|");
    }

    private static void imprimirEncabezadoInventario() {
        imprimirLineaSeparadoraInventario();
        System.out.printf("| %-35s | %-22s |%n", "Producto", "Categoria");
        imprimirLineaSeparadoraInventario();
    }

    private static void imprimirLineaSeparadoraInventario() {
        System.out.println("|-------------------------------------|------------------------|");
    }

    // ==================================================================
    // UTILIDADES DE LECTURA
    // ==================================================================

    /**
     * Lee un entero del usuario dentro del rango [min, max].
     * Repite la solicitud si el input no es válido.
     */
    private static int leerEntero(Scanner scanner, int min, int max) {
        int valor = -1;
        while (true) {
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.print("Opcion invalida. Ingrese un numero entre " + min + " y " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Entrada invalida. Ingrese un numero entre " + min + " y " + max + ": ");
            }
        }
    }

    private static String obtenerNombreImplementacion(int opcion) {
        return switch (opcion) {
            case 1 -> "HashMap";
            case 2 -> "TreeMap";
            case 3 -> "LinkedHashMap";
            default -> "Desconocida";
        };
    }
}
