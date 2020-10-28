package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Задача.
 */
public class Task implements Serializable {

    /** Номер версии класса. */
    private static final long serialVersionUID = 1L;

    /** ID задачи. */
    private final int id;

    /** Статический инкремент для ID. */
    private static int idInc = 1;

    /** Наименование задачи. */
    private String name;

    /** Описание задачи. */
    private String description;

    /** Исполнитель задачи. */
    private Employee employee;

    /** Статус задачи. */
    private Status status;

    /**
     * Задача без исполнителя.
     *
     * @param name Наименование задачи.
     * @param description Описание задачи.
     */
    public Task(String name, String description) {
        this.id = idInc++;
        this.name = name;
        this.description = description;
        this.status = Status.Ready;
        this.employee = null;
    }

    /**
     * Задача с исполнителем.
     *
     * @param name Наименование задачи.
     * @param description Описание задачи.
     * @param employee Исполнитель задачи.
     */
    public Task(String name, String description, Employee employee) {
        this(name, description);
        this.employee = employee;
        this.status = Status.Assigned;
    }

    /**
     * Назначить сотрудника.
     *
     * @param employee сотрудник.
     */
    public void assign(Employee employee){
        this.employee = employee;
        this.status = Status.Assigned;
    }

    /**
     * Переназначить сотрудника.
     *
     * @param employee сотрудник.
     */
    public void forward(Employee employee){
        this.employee = employee;
        this.status = Status.Forwarded;
    }

    /**
     * Отменить задачу
     */
    public void cancel(){
        this.status = Status.Canceled;
    }

    /**
     * Завершить задачу.
     */
    public void complete(){
        this.status = Status.Completed;
    }

    /**
     * Провалить задачу.
     */
    public void fail(){
        this.status = Status.Failed;
    }

    /**
     * Переименовать задачу.
     *
     * @param name новое имя.
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * Изменить описание задачи.
     *
     * @param description новое описание.
     */
    public void changeDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Задание" +
                " ID - " + id +
                ", название - " + name +
                ", описание - " + description +
                ", статус - " + status.toString() +
                ". Выполняет - " + (employee == null ? "Сотрудник не назначен" : employee)
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
