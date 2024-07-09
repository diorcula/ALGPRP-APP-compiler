package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T> {
    IHANLinkedList<T> HANLinkedList;

    public HANStack() {
        HANLinkedList = new HANLinkedList<>();
    }

    @Override
    public void push(T value) {
        HANLinkedList.addFirst(value);
    }

    @Override
    public T pop() {
        T poppedNode = HANLinkedList.getFirst();
        HANLinkedList.removeFirst();
        return poppedNode;
    }

    @Override
    public T peek() {
        return HANLinkedList.getFirst();
    }

    public void printStack() {
//        HANLinkedList.printList(HANLinkedList);
    }

    // delete?
    private static class ListNode<T> {
        T element;
        ListNode<T> next;

        ListNode() {
            this.element = element;
            this.next = next;
        }
    }
}
