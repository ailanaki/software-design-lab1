package org.example;

public interface BaseLRUCache<K,V> {
    public V get(K key);

    public void remove(K key);
    public void put(K key, V value);

    public int size();
    public int capacity();
    public void changeCapacity(int capacity);
}
