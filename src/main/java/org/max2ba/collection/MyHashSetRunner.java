package org.max2ba.collection;

public class MyHashSetRunner {public static void main(String[] args) {
    MyHashSet<String> myHashSet = new MyHashSet<>();

    System.out.println("Пустой ли хэшсет: " + myHashSet.isEmpty());
    System.out.println("Размер хэшсета: " + myHashSet.size());

    System.out.println("Добавление A: " + myHashSet.add("A"));
    System.out.println("Добавление B: " + myHashSet.add("B"));
    System.out.println("Добавление C: " + myHashSet.add("C"));
    System.out.println("Добавление A: " + myHashSet.add("A"));

    System.out.println("Хэшсет: " + myHashSet);
    System.out.println("Размер хэшсета: " + myHashSet.size());

    System.out.println("Есть ли в хэшсете С: " + myHashSet.contains("C"));
    System.out.println("Есть ли в хэшсете 1: " + myHashSet.contains("1"));

    System.out.println("Удаление B: " + myHashSet.remove("B"));
    System.out.println("Удаление 1: " + myHashSet.remove("1"));

    System.out.println("Хэшсет: " + myHashSet);
    System.out.println("Размер хэшсета: " + myHashSet.size());
    }
}
