package systems;

import models.Employee;
import models.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тест системы управления задачами.
 */
public class TaskSystemTest {

    /** Система управления задачами. */
    private TaskSystem taskSystem;

    @DisplayName("Инициализация.")
    @BeforeEach
    public void initialization() {

        taskSystem = new TaskSystem();

    }

    @DisplayName("Тест создания сотрудника.")
    @Test
    public void employeeCreatingTest() {

        taskSystem.addEmployee("Иван", "Андреев");
        taskSystem.addEmployee("Георгий", "Светлов");

        Assertions.assertEquals(taskSystem.getEmployeesMap().get(1).getFirstName(), "Иван");
        Assertions.assertEquals(taskSystem.getEmployeesMap().get(2).getLastName(), "Светлов");

    }


    @DisplayName("Тест создания задания.")
    @Test
    public void taskAddingTest() {

        Employee ivan = new Employee("Иван", "Андреев");

        taskSystem.addTask("Задание 1", "Описание задания 1");
        taskSystem.addTask("Задание 2", "Описание задания 2", ivan);

        //проверка заполнения полей задания
        Assertions.assertEquals("Задание 1", taskSystem.getTasksMap().get(1).getName());
        Assertions.assertEquals("Описание задания 1", taskSystem.getTasksMap().get(1).getDescription());
        Assertions.assertNull(taskSystem.getTasksMap().get(1).getEmployee());
        Assertions.assertEquals(Status.Ready, taskSystem.getTasksMap().get(1).getStatus());

        Assertions.assertEquals("Задание 2", taskSystem.getTasksMap().get(2).getName());
        Assertions.assertEquals("Описание задания 2", taskSystem.getTasksMap().get(2).getDescription());
        Assertions.assertEquals(ivan, taskSystem.getTasksMap().get(2).getEmployee());
        Assertions.assertEquals(Status.Assigned, taskSystem.getTasksMap().get(2).getStatus());

    }

}
