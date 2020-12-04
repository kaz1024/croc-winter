package ru.kaz.transport.indmobility;

/**
 * Самокат.
 */
public class KickScooter extends IndividualMobility {

    /**
     * Самокат.
     */
    public KickScooter(String make) {
        super(make);
    }

    @Override
    public String toString() {
        return "KickScooter{" +
                "make=" + getMake() +
                "}";
    }
}
