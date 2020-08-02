package ru.croc.java.winter.school.zoo.tracking.event;

import ru.croc.java.winter.school.zoo.employee.Shift;

/**
 * Событие прихода на работу и ухода с неё.
 */
public class WorkAttendanceEvent extends TrackingEvent {
    private final Shift shift;

    public WorkAttendanceEvent(Shift shifts) {
        super(shifts.getStartTime());
        this.shift = shifts;
    }

    public Shift getShift() {
        return shift;
    }
}
