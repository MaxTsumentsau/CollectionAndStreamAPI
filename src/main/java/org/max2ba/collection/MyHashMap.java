package org.max2ba.collection;

import java.util.*;

public class MyHashMap<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private Node<K, V>[] buckets;
    private int size;

    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap() {
        this.buckets = new Node[INITIAL_CAPACITY];
        this.size = 0;
    }

    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        this.buckets = new Node[INITIAL_CAPACITY];
        this.size = 0;
    }

    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        //побитовая операция получения положительного индекса, как пример
        return (hashCode & Integer.MAX_VALUE) % buckets.length;
    }


    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        size = 0;

        //перехэшировка
        for (Node<K, V> bucket : oldBuckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public boolean put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Нельзя добавлять null элементы");
        }

        if ((double) size / buckets.length >= LOAD_FACTOR) {
            resize();
        }

        int bucketIndex = getBucketIndex(key);
        Node<K, V> current = buckets[bucketIndex];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return true;
            }
            current = current.next;
        }

        buckets[bucketIndex] = new Node<>(key, value, buckets[bucketIndex]);
        size++;
        return true;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Нельзя хранить в виде ключа null элементы");
        }

        int bucketIndex = getBucketIndex(key);
        Node<K, V> current = buckets[bucketIndex];

        while (current != null) {
            if (key.equals(current.key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public boolean remove(K key) {
        if (key == null) {
            return false;
        }

        int bucketIndex = getBucketIndex(key);
        Node<K, V> current = buckets[bucketIndex];
        Node<K, V> prev = null;

        while (current != null) {
            if (key.equals(current.key)) {
                if (prev == null) {
                    buckets[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }

        int bucketIndex = getBucketIndex(key);
        Node<K, V> current = buckets[bucketIndex];

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public boolean containsValue(V value) {
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;

            while (current != null) {
                if (Objects.equals(current.value, value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.key).append("=").append(current.value);
                first = false;
                current = current.next;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();

        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;

            while (current != null) {
                keySet.add(current.key);
                current = current.next;
            }
        }

        return keySet;
    }

    public List<V> values() {
        List<V> list = new ArrayList<>(size);

        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;

            while (current != null) {
                list.add(current.value);
                current = current.next;
            }
        }

        return list;
    }
}