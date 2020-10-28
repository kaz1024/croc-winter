package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Сотрудник.
 */
public class Employee implements Serializable {

    /** Номер версии класса. */
    private static final long serialVersionUID = 1L;

    /** ID сотрудника. */
    private final int id;

    /** Статический инкремент для ID. */
    private static int idInc = 1;

    /** Имя сотрудника. */
    private final String firstName;

    /** Фамилия сотрудника. */
    private final String lastName;

    /**
     * Сотрудник.
     *
     * @param firstName Имя сотрудника.
     * @param lastName  Фамилия сотрудника.
     */
    public Employee(String firstName, String lastName) {
        this.id = idInc++;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Сотрудник" +
                " ID - " + id +
                ", Имя - " + firstName +
                ", Фамилия - " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getId() == employee.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
