package ru.kaz.zoo;

import java.time.LocalDate;

public class ZooTest {
    public static void main(String[] args) {

        /*
            за ****код стыдно, прошу прощения :D
            по собственной вине не успел сделать нормально
         */

        Zoo myZoo = new Zoo("Зоопарк плохого кода"); //создали зоопарк

        myZoo.add(new Employee("Tom")); //добавили сотрудника Тома
        myZoo.add(new Employee("Martin")); //добавили Мартина


        myZoo.add(new Animal("Tiger Bobby")); // добавили тигра Бобби
        myZoo.add(new Animal("Giraffe Linda")); // жирафа Линду

        myZoo.add(new Employee("Steve", myZoo.getAnimals()[1])); //добавили работника стива и сразу назначили ему жирафа


        myZoo.attach(myZoo.getEmployees()[0], myZoo.getAnimals()[0]); //прикрепили Тому тигра

        System.out.println(myZoo.getTitle()); // название зоопарка

        System.out.println(myZoo.getEmployees()[0].getAnimals()[0].getName()); // животное сотрудника Тома
        System.out.println(myZoo.getAnimals()[0].getOwner().getName()); // сотрудник животного тигра

        myZoo.gotSick(myZoo.getAnimals()[0], new Disease("Ветрянка", LocalDate.now().minusDays(18))); //тигр заболел 18 дней назад
        System.out.println("Название болезни: " + myZoo.getAnimals()[0].getDiseasesBook()[0].getDiseaseTitle()); //название болезни
        System.out.println("Заболел: " + myZoo.getAnimals()[0].getDiseasesBook()[0].getDiseaseStart()); //дата начала болезни

        myZoo.gotWell(myZoo.getAnimals()[0], LocalDate.now()); // и  выздоровел сегодня
        System.out.println("Выздоровел: " + myZoo.getAnimals()[0].getDiseasesBook()[0].getDiseaseEnd()); //дата выздоровления

        myZoo.delete(myZoo.getEmployees()[0]); //удалили тома
        System.out.println(myZoo.getAnimals()[0].getOwner().getName());//теперь за тигром ухаживает один из братьев-близнецов Робертов
    }

}
