package src.tda;

public class Stack<E> {
    private ListLinked<E> list;

    public Stack() {
        list = new ListLinked<>();
    }

    public void push(E element) {
        list.addLast(element); // O addFirst si prefieres
    }

    public E pop() {
        if (isEmpty()) return null;
        E top = list.get(list.size() - 1);
        list.remove(top);
        return top;
    }

    public E peek() {
        if (isEmpty()) return null;
        return list.get(list.size() - 1);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public String toString() {
        return list.toString();
    }
}

