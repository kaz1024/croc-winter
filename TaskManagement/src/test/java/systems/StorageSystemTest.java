package systems;

import models.Employee;
import models.Task;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Тест системы хранения данных
 */
public class StorageSystemTest {

    /** Система хранения данных. */
    private StorageSystem storageSystem;

    /** Файл для хранения задач. */
    private final File tasksDir = new File("tasks/tasks.txt");

    private Employee ivan;
    private Employee petr;

    private Task task1;
    private Task task2;

    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;

    @DisplayName("Инициализация.")
    @BeforeEach
    public void initialization() {
        storageSystem = new StorageSystem();

        ivan = new Employee("Иван", "Андреев");
        petr = new Employee("Георгий", "Светлов");

        task1 = new Task("Какое-то задание", "Описание какого-то задания");
        task2 = new Task("Другое задание", "Описание другого задания", ivan);

        // наверно надо это в BeforeAll?
        try {
            fileInputStream = new FileInputStream(tasksDir);
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Проверка добавления задания.")
    @Test
    public void taskAddingTest() {

        storageSystem.add(ivan);
        storageSystem.add(petr);

        Assertions.assertTrue(storageSystem.getEmployees().containsValue(ivan));
        Assertions.assertTrue(storageSystem.getEmployees().containsValue(petr));

    }

    @DisplayName("Проверка добавления сотрудника.")
    @Test
    public void employeeAddingTest() {

        storageSystem.add(task1);
        storageSystem.add(task2);

        Assertions.assertTrue(storageSystem.getTasks().containsValue(task1));
        Assertions.assertTrue(storageSystem.getTasks().containsValue(task2));

    }

    @DisplayName("Проверка записи задач в файл.")
    @Test
    public void writingToFileTest() {

        storageSystem.add(task1);
        storageSystem.add(task2);

        // записываем задачи в файл
        storageSystem.writeToFile(Arrays.asList(task1, task2));

        List<Task> tasks = new ArrayList<>();

        // считываем задачи из файла в tasks
        while (true) {
            try {
                tasks.add((Task) objectInputStream.readObject());
            } catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Assertions.assertTrue(tasks.contains(task1));
        Assertions.assertTrue(tasks.contains(task2));
    }

    @DisplayName("Проверка загрузки задач из файла.")
    @Test
    public void readingFromFileTest(){

        storageSystem.add(task1);
        storageSystem.add(task2);

        // записываем задачи в файл
        storageSystem.writeToFile(Arrays.asList(task1, task2));

        // удаляем все задачи и сотрудников из storageSystem
        storageSystem.cleanMemory();

        // считываем задачи из файла
        storageSystem.readFromFile();

        // проверяем что задачи считались
        Assertions.assertTrue(storageSystem.getTasks().containsValue(task1));
        Assertions.assertTrue(storageSystem.getTasks().containsValue(task2));

        // проверяем что сотрудники, закрепленные за задачами, считались
        Assertions.assertTrue(storageSystem.getEmployees().containsValue(ivan));
    }

    @DisplayName("Закрытие потоков")
    @AfterEach
    public void closing(){
        try {
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
