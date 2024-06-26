package nl.han.ica.datastructures;

public class Stack<T> implements IHANStack<T> {
    Node<T> node;
    LinkedList<T> linkedList;

    public Stack() {
        linkedList = new LinkedList<>();
    }

    @Override
    public void push(T value) {
        linkedList.addFirst(value);
    }

    @Override
    public T pop() {
//        node = (Node<T>) linkedList.getFirst();
        T poppedNode = linkedList.getFirst();
        linkedList.removeFirst();
        System.out.println("DE POPPED NODE IS: " + poppedNode);
        return poppedNode;
    }

    @Override
    public T peek() {
        return linkedList.getFirst();
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
