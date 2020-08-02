package ru.kaz.transport.publictransport;

/**
 * Метро.
 */
public class Metro extends PublicTransport {

    /**Вместимость по дефолту*/
    final private static int CAPACITY = 330;

    /**
     * Метро.
     */
    public Metro(String make, int capacity) {
        super(make, capacity);
    }

    public Metro(String make) {
        super(make, CAPACITY);
    }

    @Override
    public String toString() {
        return "Metro{" +
                "make=" + getMake() +
                "}";
    }
}
