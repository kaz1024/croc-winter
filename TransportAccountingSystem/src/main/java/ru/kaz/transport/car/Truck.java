package ru.kaz.transport.car;

import ru.kaz.transport.Vehicle;

/**
 * Грузовой автомобиль.
 */
public class Truck extends Car {

    /**
     * Грузовой автомобиль.
     */
    public Truck(String make) {
        super(make);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "make=" + getMake() +
                "}";
    }
}
