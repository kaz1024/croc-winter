package ru.kaz.zoo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kaz.zoo.animal.Animal;
import ru.kaz.zoo.employee.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ZooTest {
    private List<MovingRecord> movingRecordsTest;


    @BeforeEach
    public void init() {
        movingRecordsTest = new ArrayList<>();
    }

    @Test
    public void test() {
        // Сотрудники
        final Employee bob = new Employee("Боб", LocalDate.of(1980, 3, 1), 81);
        final Employee alice = new Employee("Алиса", LocalDate.of(1987, 7, 1), 45);
        // Животные
        final Animal elephant = new Animal("Слон", LocalDate.now(), 90);
        final Animal monkey = new Animal("Обезьяна", LocalDate.now(), 37);

        final Zoo zoo = new Zoo("Африка рядом");
        zoo.add(bob, alice);
        zoo.add(monkey);
        zoo.add(bob);

        zoo.move(monkey, 3, 5);
        zoo.move(monkey,100,100);

        zoo.move(elephant, 25,25);
        zoo.move(elephant, 81,12);

        //не смог написать юнит тесты для этого
        //Assertions.assertArrayEquals(movingRecordsTest, bob.getMovingRecords());
        //Пришел на работу
        zoo.move(bob, 0,0);
        //Переместился
        zoo.move(bob, 15,24);
        //Подошел к обезьяне
        zoo.move(bob, 101,101);
        //Отошел от обезьяны, зафиксировали длительность контакта
        zoo.move(bob, 65,30);
        //Переместился
        zoo.move(bob, 1,14);
        //Ушел с работы
        zoo.move(bob, 0,0);
        System.out.println(bob);
    }
}

