# Sistema de Gestión y Optimización de Inventarios en Almacenes

Este proyecto tiene como objetivo desarrollar una aplicación para modelar un almacén como un grafo dirigido y ponderado, integrando árboles B para representar las categorías de productos. La aplicación permitirá la gestión eficiente de inventarios, optimización del espacio, búsqueda de productos y visualización interactiva de las estructuras.

## Requisitos del Proyecto
- **Lenguaje**: Java
- **Herramientas**: Git, Maven/Gradle, IntelliJ IDEA o Eclipse
- **Librerías**: JGraphX, GraphStream, JavaFX + Canvas (para la visualización)

## Estructura del Repositorio
```
/ProyectoAlmacen
│
├── src/ # Código fuente principal
│ ├── grafos/ # Clases relacionadas con el grafo
│ ├── tda/ # Clases para las estructuras de datos (pila, cola, etc.)
│ └── utils/ # Funciones auxiliares y utilidades
│
├── docs/ # Documentación adicional del proyecto
│
├── .gitignore # Archivos y directorios ignorados por Git
├── README.md # Documentación principal
└── pom.xml / build.gradle # Archivos de configuración de Maven/Gradle
```
# Configuración
Abre el proyecto en tu IDE favorito.

- Ejecuta el archivo MainAlmacen.java para iniciar el sistema.

# Uso
El proyecto permite gestionar el inventario de un almacén representado como un grafo. Algunas de las funcionalidades clave son:

- Optimización de rutas de picking: Usando algoritmos como Dijkstra para calcular las rutas más cortas.

- Gestión de inventarios: Los productos están organizados jerárquicamente en árboles B+.

- Visualización interactiva: Muestra el grafo del almacén y las categorías de productos.
