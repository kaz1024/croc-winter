package ru.kaz.transport.publictransport;

import ru.kaz.transport.publictransport.PublicTransport;

/**
 * Троллейбус.
 */
public class TrolleyBus extends PublicTransport {

    /**Вместимость по дефолту*/
    final private static int CAPACITY = 110;

    /**
     * Троллейбус.
     */
    public TrolleyBus(String make, int capacity) {
        super(make, capacity);
    }

    public TrolleyBus(String make) {
        super(make, CAPACITY);
    }

    @Override
    public String toString() {
        return "TrolleyBus{" +
                "make=" + getMake() +
                "}";
    }
}
