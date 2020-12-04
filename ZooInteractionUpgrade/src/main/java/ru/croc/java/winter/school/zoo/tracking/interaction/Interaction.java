package ru.croc.java.winter.school.zoo.tracking.interaction;

import ru.croc.java.winter.school.zoo.tracking.Tracked;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Взаимодействие между двумя отслеживаемыми объектами.
 */
public class Interaction {
    /** Первый отслеживаемый объект. */
    private final Tracked a;
    /** Второй отслеживаемый объект. */
    private final Tracked b;
    /** Дата начала взаимодействия */
    private final LocalDateTime startTime;
    /** Дата окончания взаимодействия */
    private LocalDateTime finishTime;

    /**
     * Взаимодействие между двумя отслеживаемыми объектами.
     *
     * @param a первый объект
     * @param b второй объект
     * @param startTime время начала взаимодействия
     */
    public Interaction(Tracked a, Tracked b, LocalDateTime startTime) {
        this.a = a;
        this.b = b;
        this.startTime = startTime;
        finishTime = null;
    }

    /**
     * Получение другого учатника взаимодействия.
     * @param member Один участник взаимодействия.
     * @return Другой участник взаимодействия или null, если member не учавствовал во взаимодействии.
     */
    public Tracked getAnotherInteractionMember(Tracked member) {
        if (member.equals(getA())) {
            return getB();
        } else if (member.equals(getB())) {
            return getA();
        }
        return null;
    }

    /**
     * Получение продолжительности взаимодействия.
     * @return Продолжительность взаимодействия.
     */
    public long getDuration() {
        //По хорошему нужна проверка на законченность действия(finishTime != 0), не успел додумать.
        Duration duration = Duration.between(finishTime, startTime);
        // TODO Миллисекунды для того что бы в проверке не усыплять программу на минуты,
        //по хорошему конвертировать полученное количество как во второй задаче про байты
        return Math.abs(duration.toMillis());
    }

    /**
     * Время окончания взаимодействия.
     *
     * @return время, null если объекты еще взаимодействуют
     */
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    /**
     * Устанавливаем время окончания взаимодействия.
     *
     * @param finishTime время окончания
     */
    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Tracked getA() {
        return a;
    }

    public Tracked getB() {
        return b;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }



}
