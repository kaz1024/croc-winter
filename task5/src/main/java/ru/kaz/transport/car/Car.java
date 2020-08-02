package ru.kaz.transport.car;

import ru.kaz.transport.Vehicle;

/**
 * Легковой автомобиль.
 */
public class Car extends Vehicle {

    /**
     * Легковой автомобиль.
     */
    public Car(String make) {
        super(make);
    }

    @Override
    public String toString() {
        return "Car{" +
                "make=" + getMake() +
                "}";
    }
}
