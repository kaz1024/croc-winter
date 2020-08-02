package ru.kaz.zoo.employee;

import ru.kaz.zoo.MovingRecord;
import ru.kaz.zoo.Traceable;
import ru.kaz.zoo.animal.Animal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сотрудник.
 */
public class Employee implements Traceable {
    /** Имя. */
    private String name;
    /** Дата рождения. */
    private LocalDate dateOfBirth;
    /** Подопечные животные. */
    private Set<Animal> animals;
    /** Журнал перемещений сотрудника. */
    private List<MovingRecord> movingRecords = new ArrayList<>();
    /**Идентификатор датчика gps*/
    private int gpsID;
    /**Список животных которые в контакте с сотрудником*/
    private Set<Integer> animalsInContact;
    /**На работе(в зоопарке) или нет*/
    private boolean working = false;


    /**
     * Сотрудник.
     *
     * @param name ФИО
     * @param dateOfBirth дата рождения
     */
    public Employee(String name, LocalDate dateOfBirth, int gpsID) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gpsID = gpsID;
        animals = new HashSet<>();
        animalsInContact = new HashSet<>();
    }

    /**
     * Перемещение по зоопарку.
     * @param coords новые координаты
     */
    public void setLocation(double[] coords) {
        movingRecords.add(new MovingRecord(this.gpsID, coords, LocalDateTime.now(), "Переместился"));
    }

    /**
     * Приход/уход с работы.
     * @param comment Описание события
     */
    public void setLocation(double[] coords, String comment) {
        this.movingRecords.add(new MovingRecord(this.gpsID, coords, LocalDateTime.now(), comment));
    }

    /**
     * Сотрудник подошел к животному.
     * @param location координаты сотрудника
     * @param gpsID ID сотрудника
     * @param contactedGpsID ID животного
     */
    public void setLocation(double[] location, int gpsID, int contactedGpsID) {
        this.movingRecords.add(new MovingRecord(location, gpsID, contactedGpsID, LocalDateTime.now(),
                "Подошел к " + contactedGpsID));
        this.animalsInContact.add(contactedGpsID);
    }

    /**
     * Фиксация длительности контакта.
     * @param location Координаты сотрудника
     * @param gpsID ID сотрудника
     * @param contactedGpsI ID животного
     * @param minutes Продолжительность контакта
     */
    public void setLocation(double[] location, int gpsID, int contactedGpsI, long minutes) {
        this.movingRecords.add(new MovingRecord(location, gpsID, contactedGpsI, LocalDateTime.now(),
                "Пробыл в контакте с " + gpsID + " " + minutes + " Минут"));
    }

    /**
     * Проход сотрудника через ворота.
     * @param coords Координаты сотрудника
     * @return true, если прошел через ворота и false если нет
     * Если прошел через ворота, даелаем запись и меняем состояние работника на работает/не работает
     *
     */
    public boolean walkedThroughGates(double[] coords){
        if(coords[0] == 0 && coords[1] == 0) {
            if (working) {
                setLocation(coords,"Ушел с работы");
            } else {
                setLocation(coords,"Пришел на работу");
            }
            this.working = !working;
            return true;
        } else return false;
    }

    /**
     * Поручить сотруднику животное.
     *
     * @param animal животное
     */
    public void add(Animal animal) {
        animals.add(animal);
    }

    /**
     * Снимаем ответственность за животное.
     *
     * @param animal животное
     */
    public void remove(Animal animal) {
        animals.remove(animal);
    }

    /**
     * Находится ли животное на попичении?
     *
     * @param animal животное
     * @return true, если находится
     */
    public boolean isCare(Animal animal) {
        return animals.contains(animal);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public List<MovingRecord> getMovingRecords() {
        return movingRecords;
    }

    public double[] getLocation() {
        return movingRecords.get(movingRecords.size()-1).getCoords();
    }

    public Set<Integer> getAnimalsInContact() {
        return animalsInContact;
    }

    public boolean isWorking() {
        return working;
    }


    public int getGpsID() {
        return gpsID;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", animals=" + animals +
                ", movingRecords=" + movingRecords +
                ", gpsID=" + gpsID +
                ", animalsInContact=" + animalsInContact +
                ", working=" + working +
                '}';
    }
}
