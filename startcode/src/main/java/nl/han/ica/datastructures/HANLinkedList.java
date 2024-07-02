package nl.han.ica.datastructures;

import nl.han.ica.icss.checker.SemanticError;

public class HANLinkedList<T> implements IHANLinkedList<T> {
    // doordat er gebruik gemaakt wordt van stack is het niet nodig om de positie methoden uit te werken
    private Node<T> head = null;

    public HANLinkedList() {
        // maak eerst een lege list (node)
//        head = new Node<>(null, null);
    }

    @Override
    public void addFirst(T value) {
        if (head == null) {
          head = new Node<T>(value, null);
        } else {
            Node<T> newNode = new Node<T>(value, head);
            head = newNode;
        }
    }

    @Override
    public void clear() {

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
        head = head.next;
    }

    @Override
    public T getFirst() {
        if (head == null) {
            throw new RuntimeException("FOUTJE");
        }
        return head.element;
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

    public void printList(HANLinkedList<T> list) {
        Node<T> currentNode = list.head.next;
        System.out.println("DE LIST: ");

        while (currentNode != null) {
            System.out.println(currentNode.element + "---");
            currentNode = currentNode.next;
        }
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
