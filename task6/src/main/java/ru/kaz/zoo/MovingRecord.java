package ru.kaz.zoo;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Журнал перемещений.
 */
public class MovingRecord {
    /** ID gps устройства хозяина*/
    private int gpsID;
    /** ID gps устройства животного/человека в контакте*/
    private int contactedGpsID;
    /** Координаты*/
    private double[] coords;
    /** Дата записи*/
    private LocalDateTime date;
    /** Описание события*/
    private String comment;

    /**
     * Журнал перемещений.
     * @param gpsID ID Переместившегося
     * @param coords Новые координты
     * @param date Дата события
     * @param comment Описание события
     */
    public MovingRecord(int gpsID, double[] coords , LocalDateTime date, String comment) {
        this.gpsID = gpsID;
        this.coords = coords;
        this.date = LocalDateTime.now();
        this.comment = comment;
    }

    /**
     * Журнал перемещений.
     * @param coords Новые координты
     * @param gpsID ID Переместившегося
     * @param contactedGpsID ID Вошедшего в контакт
     * @param comment Описание события
     */
    public MovingRecord(double[] coords, int gpsID, int contactedGpsID, LocalDateTime date, String comment) {
        this.coords = coords;
        this.gpsID = gpsID;
        this.contactedGpsID = contactedGpsID;
        this.date = LocalDateTime.now().minusMinutes(40);
        this.comment = comment;
    }

    public double[] getCoords() {
        return coords;
    }

    public int getGpsID() {
        return gpsID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getContactedGpsID() {
        return contactedGpsID;
    }

    @Override
    public String toString() {
        return "MovingRecord{" +
                "gpsID=" + gpsID +
                ", coords=" + Arrays.toString(coords) +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                '}';
    }
}
