package models;

/**
 * Статус задачи.
 */
public enum Status {

    /** Готова к исполнению.*/
    Ready("Готова к исполнению"),

    /** Назначена. */
    Assigned("Назначена"),

    /** Провалена. */
    Failed("Провалена"),

    /** Выполнена. */
    Completed("Выполнена"),

    /** Отменена. */
    Canceled("Отменена"),

    /** Переназначена. */
    Forwarded("Переназначена");


    /** Название. */
    private final String title;

    /**
     * Статус задачи.
     */
    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
