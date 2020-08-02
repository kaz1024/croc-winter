package ru.kaz.zoo;

public interface Traceable {
    
    /**Идентификатор датчика gps*/
    int gpsID = 0;

    /**
     * Метод получения местоположения.
     */
    void setLocation(double[] location);

    

}
