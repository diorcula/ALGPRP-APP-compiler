package nl.han.ica.datastructures;

import java.util.HashMap;

public class HashmapTableLinked<K, V> {
    private final IHANLinkedList<HashMap<K, V>> scopes;

    public HashmapTableLinked() {
        this.scopes = new HANLinkedList<>();
    }

    public void pushScope() {
        this.scopes.addFirst(new HashMap<>());
    }

    public void popScope() {
        this.scopes.removeFirst();
    }

    public void putVariable(K key, V value) {
        this.scopes.getFirst().put(key, value);
    }

    public V getVariable(K key) {
        for (int i = 0; i < scopes.getSize(); i++) {
            V result = scopes.get(i).get(key);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public int getTotalAmountOfScopes() {
        return scopes.getSize();
    }
}