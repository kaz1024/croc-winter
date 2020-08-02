package ru.kaz.transport.car;

import ru.kaz.transport.car.Car;

/**
 * Легковой автомобиль.
 */
public class PassengerCar extends Car {

    /** Год производства */
    private int yearOfManufacture;

    /**
     * Легковой автомобиль.
     */
    public PassengerCar(String make, int yearOfManufacture) {
        super(make);
        this.yearOfManufacture = yearOfManufacture;
    }

    @Override
    public String toString() {
        return "PassengerCar{" +
                "make=" + getMake() +
                ", yearOfManufacture=" + yearOfManufacture +
                '}';
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }
}
