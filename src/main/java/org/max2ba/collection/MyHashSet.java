package org.max2ba.collection;

public class MyHashSet<T> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private Node<T>[] buckets;
    private int size;

    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }


    public MyHashSet() {
        this.buckets = new Node[INITIAL_CAPACITY];
        this.size = 0;
    }

    public boolean add(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Нельзя добавлять null элементы");
        }
        if ((double) size / buckets.length >= LOAD_FACTOR) {
            resize();
        }

        int bucketIndex = getBucketIndex(value);
        Node<T> current = buckets[bucketIndex];

        while (current != null) {
            if (current.value.equals(value)) {
                return false;
            }
            current = current.next;
        }

        buckets[bucketIndex] = new Node<>(value, buckets[bucketIndex]);
        size++;
        return true;
    }


    public boolean remove(T value) {
        if (value == null) {
            return false;
        }

        int bucketIndex = getBucketIndex(value);
        Node<T> current = buckets[bucketIndex];
        Node<T> prev = null;

        while (current != null) {
            if (current.value.equals(value)) {
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


    public boolean contains(T value) {
        if (value == null) {
            return false;
        }

        int bucketIndex = getBucketIndex(value);
        Node<T> current = buckets[bucketIndex];

        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    private int getBucketIndex(T value) {
        int hashCode = value.hashCode();
        // Используем побитовую операцию для получения положительного индекса
        return (hashCode & Integer.MAX_VALUE) % buckets.length;
    }


    private void resize() {
        Node<T>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        size = 0;

        //перехэшировка
        for (Node<T> bucket : oldBuckets) {
            Node<T> current = bucket;
            while (current != null) {
                add(current.value);
                current = current.next;
            }
        }
    }


    public void clear() {
        buckets = new Node[INITIAL_CAPACITY];
        size = 0;
    }


    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        boolean first = true;

        for (Node<T> bucket : buckets) {
            Node<T> current = bucket;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.value);
                first = false;
                current = current.next;
            }
        }

        sb.append("]");
        return sb.toString();
    }
}

