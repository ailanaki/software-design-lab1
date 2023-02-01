package org.example;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

public class HashMapLRUCache<K, V> extends LRUCache<K, V> {
    @NotNull
    private final HashMap<K, Node<K, V>> map;
    @Nullable
    private final Node<K, V> head;
    @Nullable
    private Node<K, V> tail;
    private int size;

    public HashMapLRUCache(int capacity) {
        super(capacity);
        this.head = this.tail = new Node<>();
        this.map = new HashMap<>();
        this.size = 0;
    }

    static class Node<K, V> {
        private Node<K, V> prev, next;
        private final K key;
        private V value;

        Node(K key, V value, Node<K, V> next, Node<K, V> prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        Node(K key, V value) {
            this(key, value, null, null);
        }

        Node() {
            this(null, null);
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

    }

    @Override
    protected V getElem(@NotNull K key) {
        if (map.containsKey(key)) {
            Node<K, V> val = map.get(key);
            deleteNode(val);
            addLast(val);
            assert this.tail == val;
            return val.getValue();
        }
        return null;
    }

    @Override
    protected void removeElem(@NotNull K key) {
        if (map.containsKey(key)) {
            int prevSize = size;
            deleteNode(map.get(key));
            map.remove(key);
            size--;
            assert size == prevSize - 1;
        }
    }

    @Override
    protected void putElem(@NotNull K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> prev = map.get(key);
            prev.value = value;
            deleteNode(prev);
            addLast(prev);
            assert this.tail == prev;
        } else {
            if (size == capacity) {
                assert head != null;
                removeElem(head.next.getKey());
            }
            int prevSize = size;
            Node<K, V> newValue = new Node<>(key, value);
            addLast(newValue);
            map.put(key, newValue);
            size++;
            assert this.tail == newValue;
            assert size == prevSize + 1;
        }
    }


    @Override
    protected int getSize() {
        return this.size;
    }

    @Override
    protected void updateCapacity(int newCapacity) {
        assert newCapacity >= 1;
        this.capacity = newCapacity;
        if (this.size >= newCapacity) {
            for (int i = 0; i < size - newCapacity + 1; ++i) {
                assert head != null;
                removeElem(head.next.getKey());
            }
            assert this.size == newCapacity;
        }
        assert this.capacity == newCapacity;
    }

    @Override
    protected void check() {
        int listSize = 0;
        Node<K, V> node = head;
        while (node != null) {
            node = node.next;
            listSize++;
        }
        listSize--;
        assert listSize == getSize();
        if (listSize == 0) {
            assert head.key == null;
            assert tail != null;
            assert tail.key == null;
        } else {
            assert tail != null;
        }
    }


    private void addLast(@NotNull Node<K, V> newTail) {
        newTail.prev = this.tail;
        newTail.next = null;
        assert this.tail != null;
        this.tail.next = newTail;
        this.tail = newTail;
    }

    private void deleteNode(@NotNull Node<K, V> node) {
        Node<K, V> prev, next;
        prev = node.prev;
        next = node.next;
        if (prev != null) {
            assert prev.next == node;
            prev.next = next;
        }
        if (next != null) {
            assert next.prev == node;
            next.prev = prev;
        }
        if (next == null) {
            this.tail = prev;
        }
    }
}
