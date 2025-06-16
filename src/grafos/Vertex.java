package src.grafos;

import src.tda.ListLinked;

public class Vertex<E> {
    private E data;
    private boolean visitado;
    protected ListLinked<Edge<E>> listAdj;

    public Vertex(E data) {
        this.data = data;
        this.visitado = false;
        this.listAdj = new ListLinked<Edge<E>>();
    }

    public E getData() {
        return data;
    }

    public boolean isVisited() {
        return visitado;
    }

    public void setVisited(boolean visitado) {
        this.visitado = visitado;
    }

    public boolean equals(Object o) {
        if (o instanceof Vertex<?>) {
            Vertex<E> v = (Vertex<E>) o;
            return this.data.equals(v.data);
        }
        return false;
    }

    public String toString() {
        return this.data + " --> " + this.listAdj.toString() + "\n";
    }
}
