package ru.croc.java.winter.school.zoo.tracking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.croc.java.winter.school.zoo.Zoo;
import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.employee.Shift;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndEmployeeInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.WorkAttendanceEvent;
import ru.croc.java.winter.school.zoo.tracking.interaction.Interaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Проверка сервиса отслеживания объектов в зоопарке.
 */
public class TrackingServiceTest {
    private Zoo zoo;
    private Employee bob;
    private Employee alise;
    private Animal elephant;
    private Animal monkey;

    @BeforeEach
    public void init() {
        // Сотрудники
        bob = new Employee("Боб", LocalDate.of(1980, 3, 1));
        alise = new Employee("Алиса", LocalDate.of(1987, 7, 1));
        // Животные
        elephant = new Animal("Слон", LocalDate.now());
        monkey = new Animal("Обезьяна", LocalDate.now());

        final Zoo zoo = new Zoo("Африка рядом");
        zoo.add(bob, alise);
        zoo.add(elephant, bob);
        zoo.add(monkey, alise);

        this.zoo = zoo;
    }

    @DisplayName("Проверка журнала отслеживания животных")
    @Test
    public void testJournalOfAnimalTracking() throws InterruptedException {
        final TrackingService trackingService = zoo.getTrackingService();

        final Animal lion = new Animal("Лев", LocalDate.of(1990, 3, 8));
        final Set<Tracked> animalsAndEmployees = new HashSet<>();
        animalsAndEmployees.addAll(zoo.getAnimals());
        animalsAndEmployees.addAll(zoo.getEmployees());
        Assertions.assertEquals(animalsAndEmployees, trackingService.getTrackable());

        Assertions.assertFalse(trackingService.getTrackable().contains(lion));
        zoo.add(lion, bob);
        Assertions.assertTrue(trackingService.getTrackable().contains(lion));

        final LocalDateTime beforeTime = LocalDateTime.now();
        Thread.sleep(1);
        trackingService.update(lion.getId(), 0, 0);
        Thread.sleep(1);
        final LocalDateTime betweenTime = LocalDateTime.now();
        Thread.sleep(1);
        trackingService.update(lion.getId(), 10, 10);
        Thread.sleep(1);
        final LocalDateTime afterTime = LocalDateTime.now();

        Assertions.assertTrue(lion.getLocations().get(0).time.isAfter(beforeTime));
        Assertions.assertTrue(lion.getLocations().get(0).time.isBefore(betweenTime));

        Assertions.assertTrue(lion.getLocations().get(1).time.isAfter(betweenTime));
        Assertions.assertTrue(lion.getLocations().get(1).time.isBefore(afterTime));

    }

    @DisplayName("Проверка отслеживания событий взаимодействия сотрудника и животного")
    @Test
    public void testInteractionEmployeeAndAnimal() {
        final TrackingService trackingService = zoo.getTrackingService();

        // начальные позиции
        trackingService.update(bob.getId(), 20, 20);
        trackingService.update(elephant.getId(), 50, 50);
        // одно событие - приход на работу
        Assertions.assertEquals(1, trackingService.getEvents().size());

        // Боб подошел к слону
        trackingService.update(bob.getId(), 50, 50);
        Assertions.assertEquals(2, trackingService.getEvents().size());
        final Interaction interaction = ((EmployeeAndAnimalInteractionEvent) trackingService.
                getEvents().get(trackingService.getEvents().size() -1 )).getInteraction();
        Assertions.assertEquals(interaction.getB(), bob);
        Assertions.assertEquals(interaction.getA(), elephant);
        Assertions.assertNull(interaction.getFinishTime());

        // Боб продолжает стоять рядом со слоном
        trackingService.update(bob.getId(), 50.01, 50.99);
        trackingService.update(elephant.getId(), 50.98, 50.001);
        Assertions.assertEquals(2, trackingService.getEvents().size());
        Assertions.assertNull(interaction.getFinishTime());

        // Слон убежал от Боба
        trackingService.update(elephant.getId(), 20.01, 70.99);
        Assertions.assertEquals(2, trackingService.getEvents().size());
        Assertions.assertNotNull(interaction.getFinishTime());

        // Боб догнал слона
        trackingService.update(bob.getId(), 20.98, 70.02);
        Assertions.assertEquals(3, trackingService.getEvents().size());
    }

    @DisplayName("Проверка отслеживания событий взаимодействия сотрудника и сотрудника")
    @Test//почти копипаст прошлого теста
    public void testInteractionEmployeeAndEmployee() {
        final TrackingService trackingService = zoo.getTrackingService();

        // начальные позиции
        trackingService.update(bob.getId(), 20, 20);
        trackingService.update(alise.getId(), 80, 80);
        //оба пришли на работу - два события
        Assertions.assertEquals(2, trackingService.getEvents().size());

        // Боб подошел к алисе
        trackingService.update(bob.getId(), 80, 80);
        Assertions.assertEquals(3, trackingService.getEvents().size());
        final Interaction interaction = ((EmployeeAndEmployeeInteractionEvent) trackingService.
                getEvents().get(trackingService.getEvents().size() -1 )).getInteraction();
        Assertions.assertEquals(interaction.getAnotherInteractionMember(alise), bob);
        Assertions.assertEquals(interaction.getAnotherInteractionMember(bob), alise);
        Assertions.assertNull(interaction.getFinishTime());

        // Боб продолжает стоять рядом алисой
        trackingService.update(bob.getId(), 80.01, 80.99);
        trackingService.update(alise.getId(), 80.98, 80.001);
        Assertions.assertEquals(3, trackingService.getEvents().size());
        Assertions.assertNull(interaction.getFinishTime());

        // Алиса в истерике убежала от Боба
        trackingService.update(alise.getId(), 80.01, 30.99);
        Assertions.assertEquals(3, trackingService.getEvents().size());
        Assertions.assertNotNull(interaction.getFinishTime());

        // Боб догнал и успокоил Алису
        trackingService.update(bob.getId(), 80.98, 30.02);
        Assertions.assertEquals(4, trackingService.getEvents().size());
    }

    @DisplayName("Проверка прихода и ухода на работу сотрудника")
    @Test
    public void testEmployeeWorkAttendance() {
        final TrackingService trackingService = zoo.getTrackingService();
        // не на работе
        trackingService.update(bob.getId(), 0, 0);
        Assertions.assertTrue(trackingService.getEvents().isEmpty());

        //всё еще не на работе
        trackingService.update(bob.getId(), 5, 5);
        Assertions.assertTrue(trackingService.getEvents().isEmpty());

        //пришел на работу
        trackingService.update(bob.getId(), 30, 30);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        final Shift shift = ((WorkAttendanceEvent) trackingService.getEvents().get(0)).getShift();
        Assertions.assertEquals(shift.getEmployee(), bob);

        //Походил внутри
        trackingService.update(bob.getId(), 80, 60);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        Assertions.assertNull(shift.getFinishTime());

        //Вышел с работы
        trackingService.update(bob.getId(), 123, 321);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        Assertions.assertNotNull(shift.getFinishTime());

        //Еще раз зашел
        trackingService.update(bob.getId(), 40, 60);
        Assertions.assertEquals(2, trackingService.getEvents().size());
    }

    @DisplayName("Проверка метода получения количества выведенных сотрудником животных")
    @Test
    public void testAnimalTheft(){
        final TrackingService trackingService = zoo.getTrackingService();
        // начальные позиции
        trackingService.update(bob.getId(), 0, 0);
        trackingService.update(alise.getId(), 80, 20);
        trackingService.update(elephant.getId(), 80, 80);

        //Боб пришел на работу
        trackingService.update(bob.getId(), 20, 20);

        //Боб подошел к алисе
        trackingService.update(bob.getId(), 80, 20);

        // Боб сходил на обед
        trackingService.update(bob.getId(), -4, -5);
        Assertions.assertEquals(0, trackingService.getStolenAnimalsNumber(bob));

        //Боб вернулся с обеда
        trackingService.update(bob.getId(), 20, 20);

        //Боб подошел к слону
        trackingService.update(bob.getId(), 80, 80);

        //И незаметно вывел его через черный вход
        trackingService.update(bob.getId(), 100, 100);
        Assertions.assertEquals(1, trackingService.getStolenAnimalsNumber(bob));
    }

    @DisplayName("Проверка времени проведенное сотрудником с его подопечными за всё время")
    @Test
    public void testTotalEmployeeInteractionWithHisAnimalsTime() throws InterruptedException {
        final TrackingService trackingService = zoo.getTrackingService();

        // начальные позиции
        trackingService.update(bob.getId(), 20, 20);
        trackingService.update(elephant.getId(), 50, 50);
        trackingService.update(monkey.getId(), 80, 80);
        Assertions.assertEquals(0, trackingService.getEmployeeTotalTimeWithHisAnimals(bob));

        // Боб подошел к слону(его животное)
        trackingService.update(bob.getId(), 50, 50);
        //постоял 100 миллисекунд
        Thread.sleep(100);

        // и пошел гулять дальше
        trackingService.update(bob.getId(), 20.01, 80.99);

        Assertions.assertNotEquals(0, trackingService.getEmployeeTotalTimeWithHisAnimals(bob));
        //засекаем время проведенное с его животным
        long firstCheck = trackingService.getEmployeeTotalTimeWithHisAnimals(bob);

        // Боб подошел к обезьяне(не его животное)
        trackingService.update(bob.getId(), 80.01, 80.99);
        //постоял 100 миллисекунд
        Thread.sleep(100);

        // и пошел гулять дальше
        trackingService.update(bob.getId(), 80.01, 20.99);

        //засекаем время проведенное с двумя животными, одно из которых - его
        long secondCheck = trackingService.getEmployeeTotalTimeWithHisAnimals(bob);
        Assertions.assertEquals(firstCheck,secondCheck);

        // Боб еще раз подошел к слону(снова его животное)
        trackingService.update(bob.getId(), 50.98, 50.02);
        Thread.sleep(100);//постоял 100 миллисекунд

        // И отошел
        trackingService.update(bob.getId(), 20.01, 80.99);

        //засекаем время проведенное с двумя животными, одно из которых - его(подходил к нему два раза)
        long thirdCheck = trackingService.getEmployeeTotalTimeWithHisAnimals(bob);
        Assertions.assertTrue(thirdCheck > secondCheck);

    }
}
