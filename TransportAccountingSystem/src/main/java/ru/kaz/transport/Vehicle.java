package ru.kaz.transport;

/**
 * Транспортное средство.
 */
public class Vehicle {

    /**Марка машины.*/
    private String make;

    /**
     * Транспортное средство.
     */
    public Vehicle(String make) {
        this.make = make;
    }

    /**
     * Транспортное средство.
     */
    public Vehicle() {
    }

    public String getMake() {
        return make;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "make='" + make + '\'' +
                '}';
    }
}
