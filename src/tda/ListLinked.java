package src.tda;

public class ListLinked<T> {
    private Nodo<T> cabeza;
    private int tamanio;

    public ListLinked() {
        cabeza = null;
        tamanio = 0;
    }

    public void addLast(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.next != null) {
                actual = actual.next;
            }
            actual.next = nuevo;
        }
        tamanio++;
    }
    public boolean remove(T dato) {
        if (cabeza == null) return false;  // Verifica si la lista está vacía antes de continuar

        // Si el nodo a eliminar está al inicio
        if (cabeza.dato == null || cabeza.dato.equals(dato)) {  // Verificación de nulos
            cabeza = cabeza.next;
            tamanio--;
            return true;
        }

        Nodo<T> anterior = cabeza;
        Nodo<T> actual = cabeza.next;

        while (actual != null) {
            if (actual.dato != null && actual.dato.equals(dato)) {  // Verificación de nulos
                anterior.next = actual.next;
                tamanio--;
                return true;
            }
            anterior = actual;
            actual = actual.next;
        }

        return false;  // No se encontró el dato
    }

    public T get(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.next;
        }
        return actual.dato;
    }

    public boolean contains(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return true;
            }
            actual = actual.next;
        }
        return false;
    }

    public int size() {
        return tamanio;
    }

    public String toString() {
        StringBuilder resultado = new StringBuilder();
        Nodo<T> actual = cabeza;
        while (actual != null) {
            resultado.append(actual.dato.toString()).append(" ");
            actual = actual.next;
        }
        return resultado.toString().trim();
    }

    public T removeFirst() {
        if (cabeza == null) {
            System.out.println("Lista vacía, no se puede eliminar el primer elemento.");
            return null;  // Si la lista está vacía, devolvemos null
        }
        T dato = cabeza.dato;
        cabeza = cabeza.next;
        tamanio--;
        return dato;
    }
    public void set(int indice, T dato) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.next;
        }
        actual.dato = dato;
    }

    public int indexOf(T dato) {
        Nodo<T> actual = cabeza;
        int indice = 0;
        while (actual != null) {
            if (actual.dato != null && actual.dato.equals(dato)) {
                return indice;
            }
            actual = actual.next;
            indice++;
        }
        return -1; // No encontrado
    }

    public void addFirst(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.next = cabeza;
        cabeza = nuevo;
        tamanio++;
    }



    public boolean isEmpty() {
        return tamanio == 0;
    }

}
