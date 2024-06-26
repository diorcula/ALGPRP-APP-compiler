package nl.han.ica.datastructures;

public class LinkedList<T> implements IHANLinkedList<T> {
    // doordat er gebruik gemaakt wordt is het niet nodig om de positie methoden uit te werken
    private Node<T> head;

    public LinkedList() {
        // maak eerst een lege list (node)
        head = new Node<>(null, null);
    }

    @Override
    public void addFirst(T value) {
        // de eerste in de lijst is de enige, daarom is next hier null, er is geen node na
        Node<T> newNode = new Node<T>(value, head);
        head = newNode;
    }

    @Override
    public void clear() {
        // maak hem helemaal leeg, brute oplossing natuurlijk
        // als er altijd een node aanwezig is lost dit implementatie problemen op
        //head=null;
        head = new Node<>(null, null);
    }

    @Override
    public void insert(int index, T value) {

    }

    @Override
    public void delete(int pos) {

    }

    @Override
    public T get(int pos) {
        return null;
    }

    @Override
    public void removeFirst() {
        // de 2e node wordt nu de head
        if (head == null) {
            System.out.println("lijst is leeg");
        } else if (head.next == null) {
            head = null;
        } else {
            head = head.next;
        }
    }

    @Override
    public T getFirst() {
        System.out.println("RETURNING: " + head.element);
        return head.element; //zie node class beneden
    }

    @Override
    public int getSize() {
        int size = 0;
        Node<T> current = head;
        while (current.next != null) {
            size++;
        }
        return size;
    }

    private static class Node<T> {
        T element;
        Node<T> next;

        Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }
    }
}
