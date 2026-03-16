# Hoja de Trabajo 6 - Operaciones con Mapas

**CC2003 – Algoritmos y Estructura de Datos**  
Universidad del Valle de Guatemala  
Semestre I – 2023

**Desarrolladores:** Diego Castillo y Henry Guzmán

---

## ¿Qué hace el programa?

Lee un archivo de inventario (`inventario.txt`) con productos y categorías de una tienda online, y le permite al usuario agregar productos a su colección personal y hacer consultas sobre ellos.

## ¿Cómo correrlo?

1. Asegúrate de tener el archivo `inventario.txt` en la misma carpeta que los `.java`
2. Compila todos los archivos:
```
javac *.java
```
3. Ejecuta:
```
java Main
```

Al iniciar, el programa te pide elegir la implementación de Map a usar:
- `1` → HashMap
- `2` → TreeMap
- `3` → LinkedHashMap

## Operaciones disponibles

| # | Descripción |
|---|-------------|
| 1 | Agregar un producto a tu colección |
| 2 | Ver la categoría de un producto |
| 3 | Ver tu colección (producto, categoría, cantidad) |
| 4 | Ver tu colección ordenada por tipo |
| 5 | Ver todo el inventario |
| 6 | Ver todo el inventario ordenado por tipo |

## Archivos del proyecto

```
├── Main.java
├── MapFactory.java
├── MapFactoryProducer.java
├── HashMapFactory.java
├── TreeMapFactory.java
├── LinkedHashMapFactory.java
├── Inventario.java
├── ColeccionUsuario.java
└── inventario.txt
```

## Notas

- El patrón Factory está implementado en `MapFactory.java` y sus clases concretas (`HashMapFactory`, `TreeMapFactory`, `LinkedHashMapFactory`).
- Si escribes un producto que no existe en el inventario, el programa muestra un error.
- Puedes agregar el mismo producto más de una vez, el programa lleva la cuenta de la cantidad.
