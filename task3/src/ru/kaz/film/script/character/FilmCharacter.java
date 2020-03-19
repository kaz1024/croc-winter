package ru.kaz.film.script.character;

public class FilmCharacter {
    private int age;
    private String name;
    private String gender;

    public int getAge() {
        return age;
    }

    public String getName() { return name; }

    public String getGender() {
        return gender;
    }

    public FilmCharacter(int age, String name, String gender) {
        this.age = age;
        this.gender = gender;
        this.name = name;
    }
}
