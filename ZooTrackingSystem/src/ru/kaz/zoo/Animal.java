package ru.kaz.zoo;

import java.time.LocalDate;
import java.util.Arrays;

public class Animal {

    private String name;
    private Employee owner;
    private Disease[] diseasesBook = new Disease[0];
    private Boolean isSick = false;

    public String getName() {
        return name;
    }

    public Employee getOwner() {
        return owner;
    }

    public Disease[] getDiseasesBook() {
        return diseasesBook;
    }

    public Boolean getSick() {
        return isSick;
    }

    public void setSick(Boolean sick) {
        isSick = sick;
    }

    public void setDiseasesBook(Disease[] diseasesBook) {
        this.diseasesBook = diseasesBook;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public Animal() {
    }

    public Animal(String name) {

        this.name = name;
    }

    public void add(Employee owner) {

        this.owner = owner;
    }


}
