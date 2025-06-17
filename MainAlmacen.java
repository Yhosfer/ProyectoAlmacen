import src.grafos.GraphLink;
import src.grafos.GraphViewer;



import java.util.Scanner;

public class MainAlmacen {
    private GraphLink<String> grafo;
    private Scanner scanner;

    public MainAlmacen() {
        // Inicializar el grafo como dirigido
        grafo = new GraphLink<>(true);
        scanner = new Scanner(System.in);

        // Configuración inicial del almacén
        configuracionInicial();
    }

    private void configuracionInicial() {
        // Crear nodos básicos del almacén
        grafo.insertVertex("Estantería 1");
        grafo.insertVertex("Estantería 2");
        grafo.insertVertex("Estantería 3");
        grafo.insertVertex("Estantería 4");
        grafo.insertVertex("Pasillo 1");
        grafo.insertVertex("Pasillo 2");
        grafo.insertVertex("Pasillo 3");
        grafo.insertVertex("Zona de Carga");

        // Conexiones básicas
        grafo.insertEdgeWeight("Estantería 1", "Pasillo 1", 10);
        grafo.insertEdgeWeight("Pasillo 1", "Estantería 1", 10);
        grafo.insertEdgeWeight("Estantería 2", "Pasillo 1", 15);
        grafo.insertEdgeWeight("Pasillo 1", "Estantería 2", 15);
        grafo.insertEdgeWeight("Estantería 3", "Pasillo 2", 20);
        grafo.insertEdgeWeight("Pasillo 2", "Estantería 3", 20);
        grafo.insertEdgeWeight("Estantería 4", "Pasillo 3", 25);
        grafo.insertEdgeWeight("Pasillo 3", "Estantería 4", 25);

        // Conexiones entre pasillos
        grafo.insertEdgeWeight("Pasillo 1", "Pasillo 2", 5);
        grafo.insertEdgeWeight("Pasillo 2", "Pasillo 1", 5);
        grafo.insertEdgeWeight("Pasillo 2", "Pasillo 3", 8);
        grafo.insertEdgeWeight("Pasillo 3", "Pasillo 2", 8);

        // Conexiones a zona de carga
        grafo.insertEdgeWeight("Pasillo 1", "Zona de Carga", 12);
        grafo.insertEdgeWeight("Zona de Carga", "Pasillo 1", 12);
        grafo.insertEdgeWeight("Pasillo 2", "Zona de Carga", 18);
        grafo.insertEdgeWeight("Zona de Carga", "Pasillo 2", 18);
        grafo.insertEdgeWeight("Pasillo 3", "Zona de Carga", 10);
        grafo.insertEdgeWeight("Zona de Carga", "Pasillo 3", 10);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL - GESTIÓN DE ALMACÉN ===");
            System.out.println("1. Agregar nueva ubicación");
            System.out.println("2. Eliminar ubicación existente");
            System.out.println("3. Agregar conexión entre ubicaciones");
            System.out.println("4. Eliminar conexión entre ubicaciones");
            System.out.println("5. Mostrar todas las ubicaciones");
            System.out.println("6. Mostrar todas las conexiones");
            System.out.println("7. Encontrar ruta más corta entre ubicaciones");
            System.out.println("8. Verificar conectividad del almacén");
            System.out.println("9. Explorar almacén (BFS/DFS)");
            System.out.println("10. Visualizar grafo del almacén");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch(opcion) {
                case 1:
                    agregarUbicacion();
                    break;
                case 2:
                    eliminarUbicacion();
                    break;
                case 3:
                    agregarConexion();
                    break;
                case 4:
                    eliminarConexion();
                    break;
                case 5:
                    mostrarUbicaciones();
                    break;
                case 6:
                    mostrarConexiones();
                    break;
                case 7:
                    encontrarRutaCorta();
                    break;
                case 8:
                    verificarConectividad();
                    break;
                case 9:
                    explorarAlmacen();
                    break;
                case 10:
                    visualizarGrafo();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while(opcion != 0);
    }

    private void agregarUbicacion() {
        System.out.println("\n--- AGREGAR NUEVA UBICACIÓN ---");
        System.out.println("Tipos disponibles:");
        System.out.println("1. Estantería");
        System.out.println("2. Pasillo");
        System.out.println("3. Zona de Carga");
        System.out.println("4. Otra ubicación");
        System.out.print("Seleccione el tipo de ubicación: ");

        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Ingrese el nombre/identificador de la ubicación: ");
        String nombre = scanner.nextLine();

        // Agregar el vértice al grafo
        grafo.insertVertex(nombre);
        System.out.println("Ubicación '" + nombre + "' agregada exitosamente.");
    }

    private void eliminarUbicacion() {
        System.out.println("\n--- ELIMINAR UBICACIÓN EXISTENTE ---");
        mostrarUbicaciones();

        System.out.print("Ingrese el nombre de la ubicación a eliminar: ");
        String nombre = scanner.nextLine();

        if(grafo.searchVertex(nombre)) {
            grafo.removeVertex(nombre);
            System.out.println("Ubicación '" + nombre + "' eliminada exitosamente.");
        } else {
            System.out.println("La ubicación '" + nombre + "' no existe en el almacén.");
        }
    }

    private void agregarConexion() {
        System.out.println("\n--- AGREGAR CONEXIÓN ENTRE UBICACIONES ---");
        mostrarUbicaciones();

        System.out.print("Ingrese la ubicación de origen: ");
        String origen = scanner.nextLine();

        System.out.print("Ingrese la ubicación de destino: ");
        String destino = scanner.nextLine();

        System.out.print("Ingrese la distancia en metros: ");
        int distancia = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if(grafo.searchVertex(origen) && grafo.searchVertex(destino)) {
            grafo.insertEdgeWeight(origen, destino, distancia);
            System.out.println("Conexión agregada: " + origen + " -> " + destino + " (" + distancia + "m)");
        } else {
            System.out.println("Error: Una o ambas ubicaciones no existen.");
        }
    }

    private void eliminarConexion() {
        System.out.println("\n--- ELIMINAR CONEXIÓN ENTRE UBICACIONES ---");
        mostrarUbicaciones();

        System.out.print("Ingrese la ubicación de origen: ");
        String origen = scanner.nextLine();

        System.out.print("Ingrese la ubicación de destino: ");
        String destino = scanner.nextLine();

        if(grafo.searchVertex(origen) && grafo.searchVertex(destino)) {
            if(grafo.searchEdge(origen, destino)) {
                grafo.removeEdge(origen, destino);
                System.out.println("Conexión eliminada: " + origen + " -> " + destino);
            } else {
                System.out.println("No existe conexión entre " + origen + " y " + destino);
            }
        } else {
            System.out.println("Error: Una o ambas ubicaciones no existen.");
        }
    }

    private void mostrarUbicaciones() {
        System.out.println("\n--- UBICACIONES EN EL ALMACÉN ---");
        // Este método debería ser implementado en GraphLink para listar todos los vértices
        System.out.println(grafo.toString());
    }

    private void mostrarConexiones() {
        System.out.println("\n--- CONEXIONES EN EL ALMACÉN ---");
        // Este método debería ser implementado en GraphLink para listar todas las aristas
        // Implementación temporal:
        System.out.println("(Listado detallado de conexiones no implementado aún)");
    }

    private void encontrarRutaCorta() {
        System.out.println("\n--- ENCONTRAR RUTA MÁS CORTA ---");
        mostrarUbicaciones();

        System.out.print("Ingrese la ubicación de origen: ");
        String origen = scanner.nextLine();

        System.out.print("Ingrese la ubicación de destino: ");
        String destino = scanner.nextLine();

        if(grafo.searchVertex(origen) && grafo.searchVertex(destino)) {
            grafo.printDijkstraDebug(origen, destino);
        } else {
            System.out.println("Error: Una o ambas ubicaciones no existen.");
        }
    }

    private void verificarConectividad() {
        System.out.println("\n--- VERIFICAR CONECTIVIDAD DEL ALMACÉN ---");
        boolean esConexo = grafo.isConexo();
        System.out.println("El almacén es " + (esConexo ? "conexo (todas las ubicaciones están conectadas)"
                : "no conexo (hay ubicaciones aisladas)"));
    }

    private void explorarAlmacen() {
        System.out.println("\n--- EXPLORAR ALMACÉN ---");
        mostrarUbicaciones();

        System.out.print("Ingrese la ubicación de inicio: ");
        String inicio = scanner.nextLine();

        if(!grafo.searchVertex(inicio)) {
            System.out.println("Error: La ubicación no existe.");
            return;
        }

        System.out.println("1. Recorrido en amplitud (BFS)");
        System.out.println("2. Recorrido en profundidad (DFS)");
        System.out.print("Seleccione el método de exploración: ");

        int metodo = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        switch(metodo) {
            case 1:
                System.out.println("\n--- BFS desde " + inicio + " ---");
                grafo.bfs(inicio);
                break;
            case 2:
                System.out.println("\n--- DFS desde " + inicio + " ---");
                grafo.dfs(inicio);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void visualizarGrafo() {

        GraphViewer.visualizar(grafo);

    }

    public static void main(String[] args) {
        MainAlmacen menu = new MainAlmacen();
        menu.mostrarMenu();
    }
}
