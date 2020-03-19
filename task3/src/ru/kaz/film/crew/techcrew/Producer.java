package ru.kaz.film.crew.techcrew;

public class Producer {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Producer(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
