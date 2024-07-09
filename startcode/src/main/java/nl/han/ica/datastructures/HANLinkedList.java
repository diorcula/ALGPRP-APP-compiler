package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T> {
    private ListNode<T> head = null;
//    private ListNode<T> head;

    public HANLinkedList() {
//        head = new ListNode<T>(null, null);
    }

    @Override
    public void addFirst(T value) {
        if (head == null) {
            head = new ListNode<T>(value, null);
        } else {
            ListNode<T> newNode = new ListNode<T>(value, head);
            head = newNode;
        }
    }

    @Override
    public void removeFirst() {
            head = head.next;
    }

    @Override
    public T getFirst() {
//        if (head == null) {
//          throw new RuntimeException("List is empty");
//        }
        return head.element;
    }

    @Override
    public int getSize() {
        int size = 0;
        ListNode<T> current = head;
        while (current.next != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    public void printList(HANLinkedList<T> list) {
        ListNode<T> currentListNode = list.head.next;
        System.out.println("DE LIST: ");

        while (currentListNode != null) {
            System.out.println(currentListNode.element + "---");
            currentListNode = currentListNode.next;
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

    private static class ListNode<T> {
        T element;
        ListNode<T> next;

        ListNode(T element, ListNode<T> next) {
            this.element = element;
            this.next = next;
        }
    }

}
