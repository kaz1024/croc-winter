package systems;

import models.Employee;
import models.Task;

import java.util.*;


/**
 * Система управления задачами.
 *
 * Класс стоит в иерархии между Menu(считыванием команд) и StorageSystem(работой с файлом)
 * и по факту работает с самими данными.
 *
 * Разделение на TaskSystem и StorageSystem сделано что бы не создавать god object.
 */
public class TaskSystem {

    /** Система хранения данных. */
    private final StorageSystem storageSystem;

    /**
     * Система управления задачами.
     */
    public TaskSystem() {
        this.storageSystem = new StorageSystem();
    }

    /**
     * Создать задачу без исполнителя.
     *
     * @param name Наименование задачи.
     * @param description Описание задачи.
     */
    public void addTask(String name, String description) {
        storageSystem.add(new Task(name, description));
    }

    /**
     * Создать задачу c исполнителем.
     *
     * @param name Наименование задачи.
     * @param description Описание задачи.
     * @param employee Исполнитель задачи.
     */
    public void addTask(String name, String description, Employee employee) {
        storageSystem.add(new Task(name, description, employee));
    }

    /**
     * Создать сотрудника.
     *
     * @param firstName Имя сотрудника.
     * @param lastName Фамилия сотрудника.
     */
    public void addEmployee(String firstName, String lastName) {
        storageSystem.add(new Employee(firstName, lastName));
    }

    /**
     * Сохранить задачи в файл.
     *
     * @param tasks Задачи.
     */
    public void save(Collection<Task> tasks){
        storageSystem.writeToFile(tasks);
        storageSystem.cleanMemory();
    }

    /**
     * Загрузить задачи из файла.
     */
    public void load() {
        storageSystem.readFromFile();
    }

    /**
     * Назначить сотрудника на задачу.
     *
     * @param task задача.
     * @param employee сотрудник.
     */
    public void assignTask(Task task, Employee employee){
        task.assign(employee);
    }

    /**
     * Переназначить задачу.
     *
     * @param task задача.
     * @param employee новый сотрудник.
     */
    public void forwardTask(Task task, Employee employee){
        task.forward(employee);
    }

    /**
     * Отменить задачу.
     *
     *
     * @param task заадча.
     */
    public void cancelTask(Task task){
        task.cancel();
    }

    /**
     * Провалить задачу.
     *
     * @param task заадча.
     */
    public void failTask(Task task){
        task.fail();
    }

    /**
     * Выполнить задачу.
     *
     * @param task заадча.
     */
    public void completeTask(Task task){
        task.complete();
    }

    /**
     * Получить список задач.
     *
     * @return Список задач.
     */
    public Collection<Task> getTasks() {
        return storageSystem.getTasks().values();
    }

    /**
     * Получить map задач.
     *
     * @return map задач.
     */
    public Map<Integer, Task> getTasksMap() {
        return storageSystem.getTasks();
    }

    /**
     * Получить список сотрудников.
     *
     * @return Список сотрудников.
     */
    public Collection<Employee> getEmployees() {
        return storageSystem.getEmployees().values();
    }

    /**
     * Получить map сотрудников.
     *
     * @return map сотрудников.
     */
    public Map<Integer, Employee> getEmployeesMap() {
        return storageSystem.getEmployees();
    }
}