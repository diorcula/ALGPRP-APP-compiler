package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T> {
    // doordat er gebruik gemaakt wordt van stack is het niet nodig om de positie methoden uit te werken
    private Node<T> head;

    public HANLinkedList() {
        // maak eerst een lege list (node)
        head = new Node<>(null, null);
    }

    @Override
    public void addFirst(T value) {
        // voegt een nieuwe node vooraan toe
        Node<T> newNode = new Node<T>(value, head.next);
        head.next = newNode;
    }

    @Override
    public void clear() {
// ngebruik hiervan niet nodig, er is altijd een node.
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
        head.next = head.next.next;
    }

    @Override
    public T getFirst() {
        System.out.println("RETURNING the first: " + head.next.element);
        return head.next.element;
    }

    @Override
    public int getSize() {
        int size = 0;
        Node<T> current = head;
        while (current.next != null) {
            size++;
            current = current.next;
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

    public void printList(HANLinkedList<T> list){
        Node<T> currentNode = list.head.next;
        System.out.println("DE LIST: ");

        while(currentNode != null){
            System.out.println(currentNode.element + "---");
            currentNode = currentNode.next;
        }
    }
}
