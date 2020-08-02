package ru.kaz.transport.publictransport;

import ru.kaz.transport.Vehicle;

/**
 * Общественный транспорт.
 */
public class PublicTransport extends Vehicle {

    /**Вместимость траспорта*/
    private int capacity;

    public PublicTransport(String make, int capacity) {
        super(make);
        this.capacity = capacity;
    }

    public PublicTransport() {
    }

    public int getCapacity() {
        return capacity;
    }
}
