package ru.croc.java.winter.school.zoo.employee;

import ru.croc.java.winter.school.zoo.tracking.Tracked;

import java.time.LocalDateTime;

/**
 * Рабочая смена.
 */
public class Shift {
    /** Работник. */
    private final Tracked employee;
    /** Начало смены. */
    private final LocalDateTime startTime;
    /** Окончание смены. */
    private LocalDateTime finishTime;

    /**
     * Рабочая смена.
     * @param employee Работник.
     * @param startTime Дата начала смены.
     */
    public Shift(Tracked employee, LocalDateTime startTime) {
        this.employee = employee;
        this.startTime = startTime;
        this.finishTime = null;
    }

    /**
     * Устанавливаем время окончания смены.
     *
     * @param finishTime время окончания смены.
     */
    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Tracked getEmployee() {
        return employee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }
}
