package org.max2ba.streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class StreamRunner {
    public static void main(String[] args) {
        List<Book> books = Arrays.asList(
                new Book(1, "Мастер и Маргарита", "Михаил Булгаков", 1967, 400),
                new Book(2, "Преступление и наказание", "Федор Достоевский", 1866, 350),
                new Book(3,"Война и мир", "Лев Толстой", 1869, 3500),
                new Book(4,"1984", "Джордж Оруэлл", 1949, 320),
                new Book(5,"Гарри Поттер", "Джоан Роулинг", 2001, 520),
                new Book(6,"Три товарища", "Эрих Мария Ремарк", 1936, 230),
                new Book(7,"Над пропастью во ржи", "Джером Сэлинджер", 1951, 410),
                new Book(8,"Анна Каренина", "Лев Толстой", 1877, 390),
                new Book(9,"Улисс", "Джеймс Джойс", 1922, 370),
                new Book(10,"Лолита", "Владимир Набоков", 1955, 450)
        );

        List<Student> students = Arrays.asList(
                new Student(1, "Алексей", 20, Arrays.asList(books.get(0), books.get(1))),
                new Student(2, "Мария", 22, Arrays.asList(books.get(2), books.get(3))),
                new Student(3, "Иван", 19, Arrays.asList(books.get(4), books.get(5))),
                new Student(4, "Ольга", 21, Arrays.asList(books.get(6), books.get(7))),
                new Student(5, "Дмитрий", 23, Arrays.asList(books.get(8), books.get(9))),
                new Student(6, "Олег", 22, Arrays.asList(books.get(8), books.get(1))),
                new Student(7, "Петр", 18, Arrays.asList(books.get(6), books.get(9)))
        );


        //Вывести в консоль каждого студента (переопределите toString)
        System.out.println("Вывести в консоль каждого студента (переопределите toString)");
        students.stream().forEach(System.out::println);
        System.out.println("========================================================================================");

        //Получить для каждого студента список книг
        System.out.println("Получить для каждого студента список книг");
        students.stream()
                .collect(Collectors.toMap(
                        Student::getName,
                        Student::getBooks
                )).forEach((n, b) -> System.out.println(n + " = " +b));
        System.out.println("========================================================================================");

        //Получить книги
        System.out.println("Получить книги");
        students.stream()
                .flatMap(student -> student.getBooks().stream())
                .forEach(System.out::println);
        System.out.println("========================================================================================");

        //Отсортировать книги по количеству страниц (Не забывайте про условия для сравнения объектов)
        System.out.println("Отсортировать книги по количеству страниц (Не забывайте про условия для сравнения " +
                "объектов)");
        students.stream().flatMap(student -> student.getBooks().stream())
                .sorted(Comparator.comparingInt(Book::getPage))
                .forEach(System.out::println);
        System.out.println("========================================================================================");

        //Оставить только уникальные книги
        System.out.println("Оставить только уникальные книги");
        students.stream()
                .flatMap(student -> student.getBooks().stream())
                .collect(Collectors.toSet())
                .forEach(System.out::println);
        System.out.println("========================================================================================");

        //Отфильтровать книги, оставив только те, которые были выпущены после 2000 года
        System.out.println("Отфильтровать книги, оставив только те, которые были выпущены после 2000 года");
        students.stream()
                .flatMap(student -> student.getBooks().stream())
                .filter(x->x.getYear()>2000)
                //.collect(Collectors.toSet())
                .forEach(System.out::println);
        System.out.println("========================================================================================");

        //Ограничить стрим на 3 элементах
        System.out.println("Ограничить стрим на 3 элементах");
        students.stream().limit(3).forEach(System.out::println);
        System.out.println("========================================================================================");

        //Получить из книг годы выпуска
        System.out.println("Получить из книг годы выпуска");
        students.stream()
                .flatMap(student -> student.getBooks().stream())
                .distinct()
                .collect(Collectors.toMap(
                        Book::getTitle,
                        Book::getYear
                ))
                .forEach((t, y) -> System.out.println(t + " = " +y));
        System.out.println("========================================================================================");

        //При помощи методов короткого замыкания (почитайте самостоятельно что это такое) вернуть Optional от книги
        System.out.println("При помощи методов короткого замыкания (почитайте самостоятельно что это такое) вернуть " +
                "Optional от книги");
        students.stream()
                .flatMap(student -> student.getBooks().stream())
                .distinct()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Книги не найдены у студентов"));
        //или дословно Optional
        System.out.println("Optional: " + students.stream()
                .flatMap(student -> student.getBooks().stream())
                .distinct()
                .findFirst());
        System.out.println("========================================================================================");

        //При помощи методов получения значения из Optional вывести в консоль год выпуска найденной книги, либо запись
        // о том, что такая книга отсутствует
        System.out.println("При помощи методов получения значения из Optional вывести в консоль год выпуска найденной" +
                " книги, либо запись");
        students.stream()
                .flatMap(student -> student.getBooks().stream())
                .filter(x->"1984".equals(x.getTitle()))
                .findFirst()
                .ifPresentOrElse(
                        x->System.out.println(x.getYear()),
                        ()->System.out.println("Данная книга отсутствует")
                );
        System.out.println("========================================================================================");
    }
}
