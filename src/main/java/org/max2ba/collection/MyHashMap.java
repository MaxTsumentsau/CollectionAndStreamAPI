package org.max2ba.collection;

import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private Node<K, V>[] buckets;
    private int size;

    private static class Node<K, V> implements Map.Entry<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }

    public MyHashMap() {
        this.buckets = new Node[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
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

    @Override
    public V put(K key, V value) {
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
                V previousValue = current.value;
                current.value = value;
                return previousValue;
            }
            current = current.next;
        }

        buckets[bucketIndex] = new Node<>(key, value, buckets[bucketIndex]);
        size++;
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            V value = m.get(key);
            this.put(key, value);
        }
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Нельзя хранить в виде ключа null элементы");
        }

        K typedKey = (K) key;
        int bucketIndex = getBucketIndex(typedKey);
        Node<K, V> current = buckets[bucketIndex];

        while (current != null) {
            if (typedKey.equals(current.key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    @Override
    public V remove(Object key) {
        if (key == null) {
            return null;
        }

        K typedKey = (K) key;
        int bucketIndex = getBucketIndex(typedKey);
        Node<K, V> current = buckets[bucketIndex];
        Node<K, V> prev = null;

        while (current != null) {
            if (typedKey.equals(current.key)) {
                if (prev == null) {
                    buckets[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }

        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        K typedKey = (K) key;

        int bucketIndex = getBucketIndex(typedKey);
        Node<K, V> current = buckets[bucketIndex];

        while (current != null) {
            if (current.key.equals(typedKey)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
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

    @Override
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

    @Override
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

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>(size);
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                set.add(current);
                current = current.next;
            }
        }
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map)) return false;
        Map<?, ?> other = (Map<?, ?>) o;
        if (other.size() != this.size) return false;

        for (Map.Entry<K, V> entry : this.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (!Objects.equals(value, other.get(key))) {
                return false;
            }
        }
        return true;
    }

    //hashCode карты-это сумма hashCode всех её entry.
    @Override
    public int hashCode() {
        int hash = 0;
        for (Map.Entry<K, V> entry : this.entrySet()) {
            hash += entry.hashCode();
        }
        return hash;
    }
}