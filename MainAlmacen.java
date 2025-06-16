import src.grafos.GraphLink;

import src.tda.Stack;

public class MainAlmacen {
    public static void main(String[] args) {
        // Crear un grafo dirigido
        GraphLink<String> grafo = new GraphLink<>(true);

        // Crear nodos que representan ubicaciones del almacén
        grafo.insertVertex("Estantería 1");
        grafo.insertVertex("Estantería 2");
        grafo.insertVertex("Estantería 3");
        grafo.insertVertex("Estantería 4");
        grafo.insertVertex("Pasillo 1");
        grafo.insertVertex("Pasillo 2");
        grafo.insertVertex("Pasillo 3");
        grafo.insertVertex("Zona de Carga");

        // Agregar aristas con distancias (representando rutas de movimiento)
        grafo.insertEdgeWeight("Estantería 1", "Pasillo 1", 10); // Distancia 10 metros
        grafo.insertEdgeWeight("Pasillo 1", "Estantería 1", 10); // Crear arista reversa
        grafo.insertEdgeWeight("Estantería 2", "Pasillo 1", 15); // Distancia 15 metros
        grafo.insertEdgeWeight("Pasillo 1", "Estantería 2", 15); // Crear arista reversa
        grafo.insertEdgeWeight("Estantería 3", "Pasillo 2", 20); // Distancia 20 metros
        grafo.insertEdgeWeight("Pasillo 2", "Estantería 3", 20); // Crear arista reversa
        grafo.insertEdgeWeight("Estantería 4", "Pasillo 3", 25); // Distancia 25 metros
        grafo.insertEdgeWeight("Pasillo 3", "Estantería 4", 25); // Crear arista reversa

        // Pasillos entre sí
        grafo.insertEdgeWeight("Pasillo 1", "Pasillo 2", 5); // Distancia 5 metros
        grafo.insertEdgeWeight("Pasillo 2", "Pasillo 1", 5); // Crear arista reversa
        grafo.insertEdgeWeight("Pasillo 2", "Pasillo 3", 8); // Distancia 8 metros
        grafo.insertEdgeWeight("Pasillo 3", "Pasillo 2", 8); // Crear arista reversa

        // Pasillos a Zona de Carga
        grafo.insertEdgeWeight("Pasillo 1", "Zona de Carga", 12); // Distancia 12 metros
        grafo.insertEdgeWeight("Zona de Carga", "Pasillo 1", 12); // Crear arista reversa
        grafo.insertEdgeWeight("Pasillo 2", "Zona de Carga", 18); // Distancia 18 metros
        grafo.insertEdgeWeight("Zona de Carga", "Pasillo 2", 18); // Crear arista reversa
        grafo.insertEdgeWeight("Pasillo 3", "Zona de Carga", 10); // Distancia 10 metros
        grafo.insertEdgeWeight("Zona de Carga", "Pasillo 3", 10); // Crear arista reversa

        // Estanterías a Zona de Carga (si se desea una ruta directa)
        grafo.insertEdgeWeight("Estantería 1", "Zona de Carga", 30); // Distancia 30 metros
        grafo.insertEdgeWeight("Zona de Carga", "Estantería 1", 30); // Crear arista reversa
        grafo.insertEdgeWeight("Estantería 2", "Zona de Carga", 25); // Distancia 25 metros
        grafo.insertEdgeWeight("Zona de Carga", "Estantería 2", 25); // Crear arista reversa
        grafo.insertEdgeWeight("Estantería 3", "Zona de Carga", 35); // Distancia 35 metros
        grafo.insertEdgeWeight("Zona de Carga", "Estantería 3", 35); // Crear arista reversa
        grafo.insertEdgeWeight("Estantería 4", "Zona de Carga", 40); // Distancia 40 metros
        grafo.insertEdgeWeight("Zona de Carga", "Estantería 4", 40); // Crear arista reversa


        // Imprimir el grafo para ver las ubicaciones y rutas
        System.out.println("Estructura del Grafo (Almacén):");

        // Ejemplo de BFS para explorar todas las ubicaciones desde Estantería 1
        System.out.println(grafo.toString());
        System.out.println("\n--- Algoritmo BFS (explorando desde Estantería 1) ---");
        grafo.bfs("Estantería 1");

        // Ejemplo de DFS para explorar todas las ubicaciones desde Estantería 1
        System.out.println("\n--- Algoritmo DFS (explorando desde Estantería 1) ---");
        grafo.dfs("Estantería 1");

        // Verificar si el grafo es conexo
        System.out.println("\n--- Verificación de Conectividad (Grafo Conexo) ---");
        boolean esConexo = grafo.isConexo();
        System.out.println("El grafo es conexo: " + esConexo);

        // Ejemplo de uso de Dijkstra para encontrar la ruta más corta desde Estantería 1 a Zona de Carga
        System.out.println("\n--- Algoritmo Dijkstra (ruta más corta desde Estantería 1 a Zona de Carga) ---");
        grafo.printDijkstraDebug("Estantería 1", "Estantería 4");


        System.out.println("Termiando ...");
    }
}
