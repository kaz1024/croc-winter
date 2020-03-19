package ru.kaz.zoo;

import sun.font.TrueTypeFont;

import java.time.LocalDate;
import java.util.Arrays;


public class Zoo {
    private String title;
    private Employee[] employees = new Employee[0];
    private Animal[] animals = new Animal[0];

    public String getTitle() { return title; }

    public Animal[] getAnimals() {
        return animals;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public Zoo(String title) {
        this.title = title;
    }

    public void add(Employee employee) {        //добавляем сотрудника
        employees = Arrays.copyOf(employees, employees.length + 1);
        employees[employees.length - 1] = employee;
    }

    public void add(Animal animal) {        //добавляем животное
        animals = Arrays.copyOf(animals, animals.length + 1);
        animals[animals.length - 1] = animal;
    }

    public void attach(Employee employee, Animal animal) {
        employee.add(animal);
        animal.add(employee);
    }

    public void delete(Animal animal) {     //удаляем животное

        Animal[] empAnimals = new Animal[animals.length-1];     //новый список животных сотрудника
        for (int i = 0, j = 0; i < animal.getOwner().getAnimals().length; i++) {    //открепление животноего от сотрудника
            if (animal.getOwner().getAnimals()[i] != animal) {
                empAnimals[j++] = animals[i];
            }
        }
        animal.getOwner().setAnimals(empAnimals);

        Animal[] newAnimals = new Animal[animals.length-1]; //новый список животных зоопарка
        for (int i = 0, j = 0; i < animals.length; i++) {   //удаляем животного из зоопарка
            if (animals[i] != animal) {
                newAnimals[j++] = animals[i];
            }
        }
        animals = newAnimals;
    }

    public void delete(Employee employee) {     //удаляем сотрудника
        Employee newEmployee = new Employee("Robert"); // браться-близнецы Роберты заботятся о всех животных которых кинули уволившиеся сотрудники
        for (int i = 0, j = 0; i < employee.getAnimals().length; i++) {     //криво открепляем от всех подопечных животных данного сотрудника
            employee.getAnimals()[i].setOwner(newEmployee); //извините
        }

        employee.setAnimals(new Animal[0]); // криво открепляем животных от сотрудника

        Employee[] newEmployees = new Employee[employees.length-1]; // удаляем сотрудника из зоопарка
        for (int i = 0, j = 0; i < employees.length; i++) {
            if (employees[i] != employee) {
                newEmployees[j++] = employees[i];
            }
        }
        employees = newEmployees;
    }

    public void gotSick(Animal animal, Disease disease) {
        Disease[] newDisease = Arrays.copyOf(animal.getDiseasesBook(), animal.getDiseasesBook().length + 1);
        newDisease[newDisease.length - 1] = disease;
        animal.setDiseasesBook(newDisease);
        animal.setSick(Boolean.TRUE);
    }

    public void gotWell(Animal animal, LocalDate date){
        if (animal.getSick() == Boolean.TRUE) {
            animal.getDiseasesBook()[animal.getDiseasesBook().length - 1].setDiseaseEnd(date);
        } else {
            System.out.println("Эта животинка здорова");
        }
    }



}