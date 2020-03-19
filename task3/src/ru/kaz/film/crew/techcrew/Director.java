package ru.kaz.film.crew.techcrew;

public class Director {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Director(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
