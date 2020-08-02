package ru.kaz.transport.indmobility;

/**
 * Велосипед.
 */
public class Bicycle extends IndividualMobility {

    /**
     * Велосипед.
     */
    public Bicycle(String make) {
        super(make);
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "make=" + getMake() +
                "}";
    }
}
