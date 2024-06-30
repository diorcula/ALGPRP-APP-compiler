package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T> {

    private LinkedListNode<T> head = null;

    public HANLinkedList() {
        // maak eerst een lege list (node)
//        head = new LinkedListNode<>(null, null);
    }

    @Override
    public void addFirst(T value) {
        // voegt een nieuwe node vooraan toe
//        Node<T> newNode = new Node<T>(value, head.next);
//        head.next = newNode;

        if (head == null) {
            head = new LinkedListNode<>(value);
        } else {
            LinkedListNode<T> toAdd = new LinkedListNode<>(value);
            toAdd.setNext(head);
            head = toAdd;
        }
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
//
//        if (pos == 0) {
//            removeFirst();
//            return;
//        }
//
//        if (pos > getSize()) {
//            return;
//        }
//
//        LinkedListNode<T> current = head;
//
//        int count = 0;
//
//        while (count != pos - 1) {
//            current = current.getNext();
//            count++;
//        }
//
//        LinkedListNode<T> toRemove = current.getNext();
//        current.setNext(toRemove.getNext());

    }

    @Override
    public T get(int pos) {
        return null;
    }

    @Override
    public void removeFirst() {
        head = head.getNext();
    }

    @Override
    public T getFirst() {
        return head.getValue();
    }

    @Override
    public int getSize() {
        int size = 0;
        LinkedListNode<T> current = head;
        while (current.getNext() != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

//    private static class Node<T> {
//        T element;
//        Node<T> next;
//
//        Node(T element, Node<T> next) {
//            this.element = element;
//            this.next = next;
//        }
    }

//    public void printList(HANLinkedList<T> list){
//        Node<T> currentNode = list.head.next;
//        System.out.println("DE LIST: ");
//
//        while(currentNode != null){
//            System.out.println(currentNode.element + "---");
//            currentNode = currentNode.next;
//        }
//    }
//}
