package org.max2ba.collection;

public class MyArrayRunner {
    public static void main(String[] args) {
        MyArrayList<String> list1 = new MyArrayList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");

        MyArrayList<String> list2 = new MyArrayList<>();
        list2.add("X");
        list2.add("Y");

        list1.addAll(list2);
        System.out.println("После addAll: " + list1);

        MyArrayList<String> list3 = new MyArrayList<>();
        list3.add("1");
        list3.add("2");

        list1.addAll(2, list3);
        System.out.println("После addAll по индексу: " + list1);

        list1.remove("1");
        list1.remove(0);
        System.out.println("После remove по индексу и объекту: " + list1);

        System.out.println("Содержит ли элемент C: " + list1.contains("C"));

        System.out.println("Размер list1: " + list1.size());

        System.out.println("Пустой ли список: " + list1.isEmpty());
        list1.clear();
        System.out.println("После очистки: " + list1.isEmpty());
    }
}
