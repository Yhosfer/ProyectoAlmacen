package src.grafos;
import src.Exceptions.*;
import src.tda.ListLinked;
import src.tda.Queue;
import src.tda.Stack;
import src.tda.PriorityQueue;

public class GraphLink<E> {
    protected ListLinked<Vertex<E>> listVertex;
    protected boolean directed;
    // No dirigidos
    public GraphLink() {
        listVertex = new ListLinked<Vertex<E>>();
        directed = false;
    }
    // para grafos dirigidos
    public GraphLink(boolean directed) {
        listVertex = new ListLinked<Vertex<E>>();
        this.directed = directed;
    }

    public void insertVertex(E data) {
        Vertex<E> nuevoVertice = new Vertex<>(data);
        if (!listVertex.contains(nuevoVertice)) {
            listVertex.addLast(nuevoVertice);
        }
    }

    private Vertex<E> getVertex(E data) {
        for (int i = 0; i < listVertex.size(); i++) {
            Vertex<E> actual = listVertex.get(i);
            if (actual.getData().equals(data)) {
                return actual;
            }
        }
        return null;
    }

    public void insertEdge(E verOri, E verDes) {
        Vertex<E> origen = getVertex(verOri);
        Vertex<E> destino = getVertex(verDes);

        if (origen == null) {
            origen = new Vertex<>(verOri);
            listVertex.addLast(origen);
        }

        if (destino == null) {
            destino = new Vertex<>(verDes);
            listVertex.addLast(destino);
        }

        Edge<E> arista = new Edge<>(destino);
        if (!origen.listAdj.contains(arista)) {
            origen.listAdj.addLast(arista);
        }

        if(!directed) {
            Edge<E> aristaInversa = new Edge<>(origen);
            if (!destino.listAdj.contains(aristaInversa)){
                destino.listAdj.addLast(aristaInversa);
            }
        }
    }

    public void insertEdgeWeight(E verOri, E verDes, int weight) {
        Vertex<E> origen = getVertex(verOri);
        Vertex<E> destino = getVertex(verDes);

        if (origen == null) {
            origen = new Vertex<>(verOri);
            listVertex.addLast(origen);
        }

        if (destino == null) {
            destino = new Vertex<>(verDes);
            listVertex.addLast(destino);
        }
        // insertamos origen --> dest
        Edge<E> arista = new Edge<>(destino, weight);
        if (!origen.listAdj.contains(arista)) {
            origen.listAdj.addLast(arista);
        }

        if(!directed) {
            Edge<E> aristaInversa = new Edge<>(origen, weight);
            if (!destino.listAdj.contains(aristaInversa)){
                destino.listAdj.addLast(aristaInversa);
            }

        }
    }

    public boolean searchVertex(E v) {
        return getVertex(v) != null;
    }

    public boolean searchEdge(E v, E z) {
        Vertex<E> origen = getVertex(v);
        Vertex<E> destino = getVertex(z);

        if (origen == null || destino == null) {
            return false;
        }

        Edge<E> arista = new Edge<>(destino);
        boolean existeArista = origen.listAdj.contains(arista);

        // Si es no dirigido, también buscar en la dirección inversa
        if (!directed && !existeArista) {
            Edge<E> aristaInversa = new Edge<>(origen);
            existeArista = destino.listAdj.contains(aristaInversa);
        }

        return existeArista;
    }

    public void removeVertex(E v) {
        Vertex<E> vertice = getVertex(v);
        if (vertice == null) return;
        /* Se busca en la lista de adyancias de cada vertice si
        alguna arista tiene como destino el vertice a eliminar
        caso existir se remueve.
         */
        for (int i = 0; i < listVertex.size(); i++) {
            Vertex<E> actual = listVertex.get(i);
            actual.listAdj.remove(new Edge<>(vertice));
        }
        // Al eliminar el vertice se elimina la lista de adyacencia.
        listVertex.remove(vertice);

    }

    public void removeEdge(E v, E z) {
        Vertex<E> origen = getVertex(v);
        Vertex<E> destino = getVertex(z);

        if (origen == null || destino == null) return;
        // Eliminar arista origen --> destino
        origen.listAdj.remove(new Edge<>(destino));
        // Si no es dirigido, elimnar tambien destino -> origen
        if (!directed) {
            destino.listAdj.remove(new Edge<>(origen));
        }
    }
    public void dfs(E v) {
        Vertex<E> inicio = getVertex(v);
        if (inicio == null) {
            return;
        }

        resetVisited(); // Importante para reiniciar el estado visitado
        dfsRecursive(inicio);
    }

    private void dfsRecursive(Vertex<E> vertice) {
        vertice.setVisited(true);
        System.out.println(vertice.getData());

        for (int i = 0; i < vertice.listAdj.size(); i++) {
            Edge<E> arista = vertice.listAdj.get(i);
            Vertex<E> vecino = arista.getRefDest();
            if (!vecino.isVisited()) {
                dfsRecursive(vecino);
            }
        }
    }

    private void resetVisited() {
        for (int i = 0; i < listVertex.size(); i++) {
            listVertex.get(i).setVisited(false);
        }
    }

    public void bfs(E v) {
        Vertex<E> inicio = getVertex(v);
        if (inicio == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        resetVisited();
        Queue<Vertex<E>> cola = new Queue<>();
        inicio.setVisited(true);

        try {
            cola.enqueue(inicio);

            while (!cola.isEmpty()) {
                Vertex<E> actual = cola.dequeue();
                System.out.println(actual.getData());

                for (int i = 0; i < actual.listAdj.size(); i++) {
                    Vertex<E> vecino = actual.listAdj.get(i).getRefDest();
                    if (!vecino.isVisited()) {
                        vecino.setVisited(true);
                        cola.enqueue(vecino);
                    }
                }
            }
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: cola vacía.");
        }
    }


    public ListLinked<E> bfsPath(E v, E z) {
        Vertex<E> inicio = getVertex(v);
        Vertex<E> fin = getVertex(z);
        ListLinked<E> camino = new ListLinked<>();

        if (inicio == null || fin == null) {
            return camino;
        }

        resetVisited();
        Queue<Vertex<E>> cola = new Queue<>();


        ListLinked<E> vertices = new ListLinked<>();
        ListLinked<E> padres = new ListLinked<>();

        inicio.setVisited(true);
        vertices.addLast(inicio.getData());
        padres.addLast(null);

        try {
            cola.enqueue(inicio);

            while (!cola.isEmpty()) {
                Vertex<E> actual = cola.dequeue();

                if (actual.equals(fin)) {
                    break;
                }


                for (int i = 0; i < actual.listAdj.size(); i++) {
                    Vertex<E> vecino = actual.listAdj.get(i).getRefDest();
                    if (!vecino.isVisited()) {
                        vecino.setVisited(true);
                        vertices.addLast(vecino.getData());
                        padres.addLast(actual.getData()); // El padre del vecino es actual
                        cola.enqueue(vecino);
                    }
                }
            }
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: cola vacía.");
        }

        E actual = fin.getData();
        ListLinked<E> caminoReverso = new ListLinked<>();

        boolean encontrado = false;
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(actual)) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return camino;
        }

        while (actual != null) {
            caminoReverso.addLast(actual);

            E padre = null;
            for (int i = 0; i < vertices.size(); i++) {
                if (vertices.get(i).equals(actual)) {
                    padre = padres.get(i);
                    break;
                }
            }
            actual = padre;
        }


        for (int i = caminoReverso.size() - 1; i >= 0; i--) {
            camino.addLast(caminoReverso.get(i));
        }

        return camino;
    }

    public ListLinked<E> shortPath(E v, E z) {
        return bfsPath(v, z);
    }

    public boolean isConexo() {
        if (listVertex.isEmpty()) return false;

        Vertex<E> inicio = listVertex.get(0);
        resetVisited();

        Queue<Vertex<E>> cola = new Queue<>();
        inicio.setVisited(true);

        try {
            cola.enqueue(inicio);

            while (!cola.isEmpty()) {
                Vertex<E> actual = cola.dequeue();

                for (int i = 0; i < actual.listAdj.size(); i++) {
                    Vertex<E> vecino = actual.listAdj.get(i).getRefDest();
                    if (!vecino.isVisited()) {
                        vecino.setVisited(true);
                        cola.enqueue(vecino);
                    }
                }
            }
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: cola vacía.");
        }

        for (int i = 0; i < listVertex.size(); i++) {
            if (!listVertex.get(i).isVisited()) {
                return false;
            }
        }
        return true;
    }

    public Stack<E> dijkstra(E v, E w) {
        Vertex<E> inicio = getVertex(v);
        Vertex<E> fin = getVertex(w);
        Stack<E> ruta = new Stack<>();

        if (inicio == null || fin == null) {
            return ruta;
        }

        // Usar listas paralelas para manejar el estado de Dijkstra
        ListLinked<E> vertices = new ListLinked<>();
        ListLinked<Integer> distancias = new ListLinked<>();
        ListLinked<E> padres = new ListLinked<>();
        ListLinked<Boolean> visitados = new ListLinked<>();

        // Inicializar todos los vértices
        for (int i = 0; i < listVertex.size(); i++) {
            E vertexData = listVertex.get(i).getData();
            vertices.addLast(vertexData);
            distancias.addLast(vertexData.equals(v) ? 0 : Integer.MAX_VALUE);
            padres.addLast(null);
            visitados.addLast(false);
        }

        PriorityQueue<E> pq = new PriorityQueue<>();
        pq.add(v, 0);

        while (!pq.isEmpty()) {
            E actual = pq.poll();

            // Obtener índice del vértice actual
            int indiceActual = vertices.indexOf(actual);
            if (indiceActual == -1 || visitados.get(indiceActual)) {
                continue;
            }

            // Marcar como visitado
            visitados.set(indiceActual, true);

            // Si llegamos al destino, podemos terminar
            if (actual.equals(w)) {
                break;
            }

            Vertex<E> verticeActual = getVertex(actual);
            int distActual = distancias.get(indiceActual);

            // Explorar vecinos
            for (int i = 0; i < verticeActual.listAdj.size(); i++) {
                Edge<E> arista = verticeActual.listAdj.get(i);
                E vecino = arista.getRefDest().getData();
                int peso = (arista.weight > -1) ? arista.weight : 1;
                int nuevaDist = distActual + peso;

                int indiceVecino = vertices.indexOf(vecino);
                if (indiceVecino != -1 && !visitados.get(indiceVecino)) {
                    int distVecino = distancias.get(indiceVecino);

                    if (nuevaDist < distVecino) {
                        distancias.set(indiceVecino, nuevaDist);
                        padres.set(indiceVecino, actual);
                        pq.add(vecino, nuevaDist);
                    }
                }
            }
        }

        // Reconstruir el camino
        int indiceDestino = vertices.indexOf(w);
        if (indiceDestino == -1 || distancias.get(indiceDestino) == Integer.MAX_VALUE) {
            return ruta; // No hay camino
        }

        // Construir el camino desde el destino hacia el origen
        ListLinked<E> caminoTemp = new ListLinked<>();
        E actual = w;

        while (actual != null) {
            caminoTemp.addFirst(actual); // Usar addFirst para construir en orden correcto
            int indice = vertices.indexOf(actual);
            actual = (indice != -1) ? padres.get(indice) : null;
        }

        // Convertir a Stack
        for (int i = caminoTemp.size() - 1; i >= 0; i--) {
            ruta.push(caminoTemp.get(i));
        }

        return ruta;
    }
    // Versión mejorada del método printDijkstraDebug
    public void printDijkstraDebug(E v, E w) {
        System.out.println("=== DEBUG DIJKSTRA ===");
        System.out.println("Desde: \"" + v + "\" hacia: \"" + w + "\"");

        // Verificar que ambos vértices existen
        if (!verifyVerticesExist(v, w)) {
            System.out.println("No se puede ejecutar Dijkstra: uno o ambos vértices no existen");
            listAllVertices();
            System.out.println("=====================");
            return;
        }

        Stack<E> resultado = dijkstra(v, w);

        if (resultado.isEmpty()) {
            System.out.println("No hay camino disponible entre los vértices");
        } else {
            System.out.println("Camino encontrado:");
            Stack<E> temp = new Stack<>();
            int distanciaTotal = calcularDistanciaTotal(resultado);

            // Mostrar el camino sin destruir el stack original
            while (!resultado.isEmpty()) {
                E nodo = resultado.pop();
                temp.push(nodo);
                System.out.print("\"" + nodo + "\"");
                if (!resultado.isEmpty()) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
            System.out.println("Distancia total: " + distanciaTotal + " metros");

            // Restaurar el stack original
            while (!temp.isEmpty()) {
                resultado.push(temp.pop());
            }
        }
        System.out.println("=====================");
    }

    // ============== MÉTODO AUXILIAR PARA DEBUGGING ==============

    public void listAllVertices() {
        System.out.println("=== VÉRTICES DISPONIBLES ===");
        for (int i = 0; i < listVertex.size(); i++) {
            System.out.println((i + 1) + ". \"" + listVertex.get(i).getData() + "\"");
        }
        System.out.println("============================");
    }

    public boolean verifyVerticesExist(E v1, E v2) {
        boolean exists1 = searchVertex(v1);
        boolean exists2 = searchVertex(v2);

        if (!exists1) {
            System.out.println("ERROR: El vértice \"" + v1 + "\" no existe en el grafo");
        }
        if (!exists2) {
            System.out.println("ERROR: El vértice \"" + v2 + "\" no existe en el grafo");
        }

        return exists1 && exists2;
    }



    // Método auxiliar para calcular la distancia total del camino
    private int calcularDistanciaTotal(Stack<E> camino) {
        if (camino.isEmpty()) return 0;

        // Convertir stack a array para no modificarlo
        E[] caminoArray = (E[]) new Object[camino.size()];
        Stack<E> temp = new Stack<>();
        int index = 0;

        while (!camino.isEmpty()) {
            E nodo = camino.pop();
            caminoArray[index++] = nodo;
            temp.push(nodo);
        }

        // Restaurar el stack
        while (!temp.isEmpty()) {
            camino.push(temp.pop());
        }

        // Calcular distancia total
        int distanciaTotal = 0;
        for (int i = caminoArray.length - 1; i > 0; i--) {
            E origen = caminoArray[i];
            E destino = caminoArray[i-1];

            Vertex<E> verticeOrigen = getVertex(origen);
            if (verticeOrigen != null) {
                for (int j = 0; j < verticeOrigen.listAdj.size(); j++) {
                    Edge<E> arista = verticeOrigen.listAdj.get(j);
                    if (arista.getRefDest().getData().equals(destino)) {
                        int peso = (arista.weight > -1) ? arista.weight : 1;
                        distanciaTotal += peso;
                        break;
                    }
                }
            }
        }

        return distanciaTotal;
    }

    public ListLinked<E> getAllVertices() {
        ListLinked<E> vertices = new ListLinked<>();
        for (int i = 0; i < listVertex.size(); i++) {
            vertices.addLast(listVertex.get(i).getData());
        }
        return vertices;
    }

    public String getAllEdges() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listVertex.size(); i++) {
            Vertex<E> v = listVertex.get(i);
            for (int j = 0; j < v.listAdj.size(); j++) {
                Edge<E> e = v.listAdj.get(j);
                sb.append(v.getData()).append(" -> ").append(e.getRefDest().getData())
                        .append(" (").append(e.weight).append("m)\n");
            }
        }
        return sb.toString();
    }

    public String toString() {
        return this.listVertex.toString();
    }
}