package ru.kaz.transport.publictransport;

import ru.kaz.transport.publictransport.PublicTransport;

/**
 * Трамвай.
 */
public class Tram extends PublicTransport {

    /**Вместимость по дефолту*/
    final private static int CAPACITY = 260;

    /**
     * Трамвай.
     */
    public Tram(String make, int capacity) {
        super(make, capacity);
    }

    public Tram(String make) {
        super(make, CAPACITY);
    }

    @Override
    public String toString() {
        return "Tram{" +
                "make=" + getMake() +
                "}";
    }
}
