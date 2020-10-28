package systems;

import models.Employee;
import models.Task;

import java.io.*;
import java.util.*;

/**
 * Система хранения данных.
 *
 * Класс хранит данные и работает с файловой системой.
 *
 * Разделение на TaskSystem и StorageSystem сделано что бы не создавать god object.
 */
public class StorageSystem {

    /** Список задач. */
    private final Map<Integer, Task> tasks = new TreeMap<>();

    /** Список сотрудников. */
    private final Map<Integer, Employee> employees = new TreeMap<>();

    /** Файл для хранения задач. */
    private final File tasksDir = new File("tasks/tasks.txt");

    /**
     * Добавление задачи.
     *
     * @param task Задача.
     */
    public void add(Task task){
        tasks.put(task.getId(), task);
    }

    /**
     * Добавление сотрудника.
     *
     * @param employee Сотрудник.
     */
    public void add(Employee employee){
        employees.put(employee.getId(), employee);
    }

    /**
     * Записывание задач в файл.
     *
     * @param tasks Список задач.
     */
    public void writeToFile(Collection<Task> tasks) {
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(tasksDir);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for(Task task:tasks){
                objectOutputStream.writeObject(task);
            }

            fileOutputStream.close();
            objectOutputStream.close();
            System.out.println("Задачи успешно сохранены в файл!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка задач из файла.
     */
    public void readFromFile() {

        try {

            FileInputStream fileInputStream = new FileInputStream(tasksDir);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while (true) {
                try {

                    // считываем задание
                    Task task = (Task) objectInputStream.readObject();

                    // в список сотрудников кладем сотрудника, назначенного на задачу(если такой есть)
                    if(!(task.getEmployee() == null)){
                        employees.put(task.getEmployee().getId(), task.getEmployee());
                    }

                    // в список задач кладем задачу
                    tasks.put(task.getId(), task);

                    // конец файла
                } catch (EOFException e) {
                    System.out.println("Задачи успешно загружены из файла!");
                    fileInputStream.close();
                    objectInputStream.close();
                    break;
                }
            }

        } catch (StreamCorruptedException e){
            System.out.println("Невозможно считать данные, возможно файл поврежден!");
        } catch (EOFException e) {
            System.out.println("Файл пуст!");
        } catch (FileNotFoundException e) {
            System.out.println("Файл отсуствует!");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получить map задач.
     *
     * @return map задач.
     */
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    /**
     * Получить map сотрудников.
     *
     * @return map сотрудников.
     */
    public Map<Integer, Employee> getEmployees() {
        return employees;
    }

    /**
     * Очистка памяти.
     */
    public void cleanMemory(){
        tasks.clear();
    }

}