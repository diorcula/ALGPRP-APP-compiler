package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T> {
    HANLinkedList<T> HANLinkedList;

    public HANStack() {
        HANLinkedList = new HANLinkedList<>();
    }

    @Override
    public void push(T value) {
        HANLinkedList.addFirst(value);
    }

    @Override
    public T pop() {
        if (HANLinkedList.getSize() == 0) {
            System.out.println("LIST IS LEEG");
            return null;
        } else {
            System.out.println("ELSE");
            T poppedNode = HANLinkedList.getFirst();
            HANLinkedList.removeFirst();
            System.out.println("DE POPPED NODE IS: " + poppedNode);

            return poppedNode;
        }
    }

    @Override
    public T peek() {
        if (HANLinkedList.getSize() == 0) {
            System.out.println("LIST IS LEEG");
            return null;
        } else {
            System.out.println(HANLinkedList.getFirst());
            return HANLinkedList.getFirst();
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

    public void printStack(){
        HANLinkedList.printList(HANLinkedList);
    }
}
