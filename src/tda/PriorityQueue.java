package src.tda;

public class PriorityQueue<E> {
    private ListLinked<E> elements;
    private ListLinked<Integer> priorities;

    public PriorityQueue() {
        elements = new ListLinked<>();
        priorities = new ListLinked<>();
    }

    public void add(E element, int priority) {
        // Verificar si el elemento ya existe
        int existingIndex = -1;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(element)) {
                existingIndex = i;
                break;
            }
        }

        if (existingIndex != -1) {
            // Si existe y la nueva prioridad es mejor, actualizar
            if (priority < priorities.get(existingIndex)) {
                priorities.set(existingIndex, priority);
            }
            return;
        }

        // Insertar en orden de prioridad (menor prioridad = mayor importancia)
        boolean inserted = false;
        for (int i = 0; i < priorities.size(); i++) {
            if (priority < priorities.get(i)) {
                insertAt(i, element, priority);
                inserted = true;
                break;
            }
        }

        if (!inserted) {
            elements.addLast(element);
            priorities.addLast(priority);
        }
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public E poll() {
        if (isEmpty()) return null;

        // El primer elemento ya tiene la menor prioridad
        E result = elements.get(0);
        removeAt(0);
        return result;
    }

    public void updatePriority(E element, int priority) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(element)) {
                // Remover y volver a agregar con nueva prioridad
                removeAt(i);
                elements.addLast(element);
                priorities.addLast(priority);
                return;
            }
        }
    }

    public boolean contains(E element) {
        return elements.contains(element);
    }

    public int size() {
        return elements.size();
    }

    // Método auxiliar para remover por índice
    private void removeAt(int index) {
        if (index == 0) {
            elements.removeFirst();
            priorities.removeFirst();
        } else {
            // Crear nuevas listas sin el elemento en el índice dado
            ListLinked<E> newElements = new ListLinked<>();
            ListLinked<Integer> newPriorities = new ListLinked<>();

            for (int i = 0; i < elements.size(); i++) {
                if (i != index) {
                    newElements.addLast(elements.get(i));
                    newPriorities.addLast(priorities.get(i));
                }
            }

            elements = newElements;
            priorities = newPriorities;
        }
    }

    private void insertAt(int index, E element, int priority) {
        ListLinked<E> newElements = new ListLinked<>();
        ListLinked<Integer> newPriorities = new ListLinked<>();

        // Copiar elementos antes del índice
        for (int i = 0; i < index; i++) {
            newElements.addLast(elements.get(i));
            newPriorities.addLast(priorities.get(i));
        }

        // Insertar nuevo elemento
        newElements.addLast(element);
        newPriorities.addLast(priority);

        // Copiar elementos después del índice
        for (int i = index; i < elements.size(); i++) {
            newElements.addLast(elements.get(i));
            newPriorities.addLast(priorities.get(i));
        }

        elements = newElements;
        priorities = newPriorities;
    }

}