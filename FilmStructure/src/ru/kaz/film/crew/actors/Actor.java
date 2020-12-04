package ru.kaz.film.crew.actors;

public class Actor {
    private String name;
    int age;

    public String getName() { return name; }

    public int getAge() { return age; }

    public Actor(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
