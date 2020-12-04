package ru.kaz.zoo.animal;

import ru.kaz.zoo.Traceable;
import ru.kaz.zoo.MovingRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Животное.
 */
public class Animal implements Traceable {
    /** Название. */
    private String name;
    /** Дата рождения. */
    private LocalDate dateBirth;
    /** Журнал болезней. */
    private List<IllnessRecord> illnessRecords;
    /** Журнал перемещений животного. */
    private List<MovingRecord> movingRecords = new ArrayList<>();
    /**Идентификатор датчика gps*/
    private int gpsID;

    /**
     * Животное.
     *
     * @param name название
     * @param dateBirth дата рождения
     */
    public Animal(String name, LocalDate dateBirth, int gpsID) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.gpsID = gpsID;
        illnessRecords = new ArrayList<>();
    }

    /**
     * Добавляем запись в журнал болезней.
     *
     * @param illnessRecord запись о болезни.
     */
    public void add(IllnessRecord illnessRecord) {
        illnessRecords.add(illnessRecord);
    }

    /**
     * Перемещение по зоопарку.
     * @param coords новые координаты
     */
    public void setLocation(double[] coords) {
        movingRecords.add(new MovingRecord(this.gpsID, coords, LocalDateTime.now(), "Переместился"));
    }

    public double[] getLocation() {
        return movingRecords.get(movingRecords.size()-1).getCoords();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public List<IllnessRecord> getIllnessRecords() {
        return illnessRecords;
    }

    public int getGpsID() {
        return gpsID;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", dateBirth=" + dateBirth +
                ", illnessRecords=" + illnessRecords +
                ", movingRecords=" + movingRecords +
                ", gpsID=" + gpsID +
                '}';
    }
}
