package org.max2ba.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {
    private MyHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyHashMap<>();
    }

    @Test
    void size() {
        assertEquals(0, map.size(), "Size should be 0");
        map.put("Max", 34);
        assertEquals(1, map.size(), "Size should be 1");
        map.put("Max", 34);
        assertEquals(1, map.size(), "Size should be 1");
        map.put("Donald", 78);
        assertEquals(2, map.size(), "Size should be 2");
    }

    @Test
    void isEmpty() {
        assertTrue(map.isEmpty(), "New map should be empty");

        map.put("Max", 34);
        assertFalse(map.isEmpty(), "New map should not be empty");
    }

    @Test
    void clear() {
        map.put("Max", 34);
        assertFalse(map.isEmpty(), "New map should not be empty");
        map.clear();
        assertTrue(map.isEmpty(), "Cleared map should be empty");
    }

    @Test
    void put() {
        map.put("Max", 34);
        assertFalse(map.isEmpty(), "New map should not be empty");
        assertEquals(1, map.size(), "Map size should be 1");
        map.put("Max", 33);
        assertEquals(1, map.size(), "Map size should be 1");
    }

    @Test
    void shouldHandleCollisions() {
        map.put("Aa", 1);
        map.put("BB", 2);
        assertEquals(1, map.get("Aa"));
        assertEquals(2, map.get("BB"));
        assertEquals(2, map.size());
    }

    @Test
    void putAll() {
        map.put("Max", 34);
        Map<String, Integer> added = new HashMap<>();
        added.put("Max", 34);
        added.put("Mike", 30);
        map.putAll(added);
        assertEquals(2, map.size(), "Size of map should be 2");
    }

    @Test
    void get() {
        map.put("Max", 34);
        map.put("Mike", 30);
        assertEquals(34, map.get("Max"), "Should return Max");
        assertNull(map.get("Donald"), "Should return null");
    }

    @Test
    void remove() {
        map.put("Max", 34);
        map.put("Mike", 30);
        map.remove("Max");
        assertNull(map.get("Max"), "Should return null");
        map.remove("Mike", 33);
        assertEquals(30, map.get("Mike"), "Should not delete from map");
        map.remove("Mike", 30);
        assertEquals(0, map.size(), "Should delete Mike from map");
    }

    @Test
    void containsKey() {
        map.put("Max", 34);
        map.put("Mike", 30);
        assertTrue(map.containsKey("Max"), "Should contains Max");
        assertFalse(map.containsKey("Donald"), "Should not contains Donald");
    }

    @Test
    void containsValue() {
        map.put("Max", 34);
        map.put("Mike", 30);
        assertTrue(map.containsValue(34), "Should contains 34");
        assertFalse(map.containsValue(80), "Should not contains 80");
    }

    @Test
    void keySet() {
        map.put("Max", 34);
        map.put("Mike", 30);
        assertTrue(map.keySet().contains("Max") && map.keySet().contains("Mike"));
        assertFalse(map.keySet().contains("Donald"));
    }

    @Test
    void values() {
        map.put("Max", 34);
        map.put("Mike", 30);
        assertTrue(map.values().contains(34) && map.values().contains(30));
        assertFalse(map.values().contains(78));
    }

    @Test
    void entrySet() {
        map.put("Max", 34);
        map.put("Mike", 30);
        map.put("Donald", 78);

        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        map.entrySet().forEach(System.out::println);

        assertEquals(3, entries.size());
        assertTrue(entries.contains(new AbstractMap.SimpleEntry<>("Max", 34)));
        assertTrue(entries.contains(new AbstractMap.SimpleEntry<>("Mike", 30)));
        assertTrue(entries.contains(new AbstractMap.SimpleEntry<>("Donald", 78)));
        assertFalse(entries.contains(new AbstractMap.SimpleEntry<>("Clint", 90)));
        assertEquals(3, entries.size());
    }

    @Test
    void testEquals() {
        map.put("Max", 34);
        map.put("Mike", 30);
        map.put("Donald", 78);

        Map<String, Integer> other = new MyHashMap<>();
        other.put("Max", 34);
        other.put("Mike", 30);

        assertFalse(map.equals(other));
        other.put("Donald", 78);

        assertTrue(map.equals(other));

    }

    @Test
    void testHashCode() {
        map.put("Max", 34);
        map.put("Mike", 30);
        map.put("Donald", 78);

        Map<String, Integer> other = new MyHashMap<>();
        other.put("Max", 34);
        other.put("Mike", 30);

        assertNotEquals(map.hashCode(), other.hashCode());
        other.put("Donald", 78);

        assertEquals(map.hashCode(), other.hashCode());
    }

    @Test
    void stressTestPutAndGet() {
        int n = 10000;

        for (int i = 0; i < n; i++) {
            map.put("key" + i, i);
        }
        assertEquals(n, map.size());

        for (int i = 0; i < n; i++) {
            assertEquals(i, map.get("key" + i));
        }
    }

    @Test
    void stressTestResize() {
        for (int i = 0; i < 1_000_000; i++) {
            map.put("k" + i, i);
        }

        for (int i = 0; i < 1_000_000; i++) {
            assertEquals(i, map.get("k" + i));
        }
    }

}