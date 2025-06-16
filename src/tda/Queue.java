package src.tda;
import src.Exceptions.*;


public class Queue<E> {
    private Nodo<E> first;
    private Nodo<E> last;
    private int size;

    public Queue() {
        first = null;
        last = null;
        size = 0;
    }

    public void enqueue(E e) {
        Nodo<E> newNodo = new Nodo<>(e);
        if(isEmpty()){
            first = last = newNodo;
        } else {
            last.setNext(newNodo);
        }
        last = newNodo;
        size++;
    }

    public E dequeue() throws ExceptionIsEmpty {
        if(isEmpty()){
            throw new ExceptionIsEmpty();
        } else {
            E item = first.getDato();
            first = first.getNext();
            size--;
            return item;
        }
    }

    public E front() throws ExceptionIsEmpty {
        if(isEmpty()){
            throw new ExceptionIsEmpty();
        } else {
            return first.getDato();
        }
    }

    public E back() throws ExceptionIsEmpty {
        if(isEmpty()){
            throw new ExceptionIsEmpty();
        } else {
            return last.getDato();
        }
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public String toString() {
        return "LinkedQueue{" +
                "first=" + first +
                ", last=" + last +
                '}';
    }
}

