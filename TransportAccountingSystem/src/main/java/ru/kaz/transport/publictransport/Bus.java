package ru.kaz.transport.publictransport;

/**
 * Автобус
 */
public class Bus extends PublicTransport {

    /**Вместимость по дефолту*/
    final private static int CAPACITY = 40;

    /**
     * Автобус
     */
    public Bus(String make, int capacity) {
        super(make, capacity);
    }

    public Bus(String make) {
        super(make, CAPACITY);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "make=" + getMake() +
                "}";
    }
}