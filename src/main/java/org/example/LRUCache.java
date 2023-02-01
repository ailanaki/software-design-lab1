package org.example;

public abstract class LRUCache<K, V> implements BaseLRUCache<K, V> {
    protected int capacity;

    public LRUCache(int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
    }

    @Override
    public V get(K key) {
        assert key != null;
        final V result = getElem(key);
        check();
        return result;
    }

    @Override
    public void remove(K key){
        assert key != null;
        removeElem(key);
        check();
    }

    @Override
    public void put(K key, V value) {
        assert key != null;
        assert value != null;

        final int startSize = size();
        putElem(key, value);
        final int endSize = size();

        assert getElem(key) == value;
        assert startSize + 1 == endSize || endSize == capacity;
        check();
    }

    @Override
    public int size() {
        final int size = getSize();
        assert size <= capacity;
        return size;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    public void changeCapacity(int capacity) {
        updateCapacity(capacity);
        check();
    }

    protected abstract V getElem(K key);

    protected abstract void removeElem(K key);

    protected abstract void putElem(K key, V value);

    protected abstract int getSize();

    protected abstract void updateCapacity(int capacity);

    protected abstract void check();
}
