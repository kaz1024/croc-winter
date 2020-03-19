package ru.kaz.zoo;

import java.util.Arrays;

public class Employee {
    private String name;
    private Animal[] animals = new Animal[0];

    public String getName() {
        return name;
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public void setAnimals(Animal[] animals) {
        this.animals = animals;
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, Animal animal) {
        this.name = name;
        animals = Arrays.copyOf(animals, animals.length + 1);
        animals[animals.length - 1] = animal;
    }

    public void add(Animal animal) {
        animals = Arrays.copyOf(animals, animals.length + 1);
        animals[animals.length - 1] = animal;
    }

}
