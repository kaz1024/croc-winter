package systems;

import models.Employee;
import models.Status;
import models.Task;

import java.util.*;

/**
 * Консольное меню для работы с программой.
 *
 * Класс не работает с данными, только считывает пользовательский ввод и передает данные
 * в Task System.
 */
public class Menu {

    /** Система управления задачами. */
    private final TaskSystem taskSystem;

    /** "Ресурсный" класс для хранения строк. */
    private final Strings strings;

    /** Два сканнера, так как один некорректно работает с разными типами. */
    private final Scanner strScanner = new Scanner(System.in);
    private final Scanner intScanner = new Scanner(System.in);

    /** Условие(флаг) работы программы. */
    boolean run = true;

    /**
     * Консольное меню для работы с программой.
     */
    public Menu() {
        strings = new Strings();
        taskSystem = new TaskSystem();
    }

    /**
     * Запуск программы.
     */
    public void start() {

        do {

            // вывод главного меню
            System.out.print(strings.mainMenu);

            // запрашиваем команду до тех пор пока не будет введена цифра из переданного списка
            switch (checkInput(strings.commandRequest, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 0))) {
                case 1:

                    // добавить задачу
                    taskAdding();
                    continue;

                case 2:
                    // добавить сотрудника
                    employeeAdding();
                    continue;

                case 3:
                    // вывести список задач
                    showTasks(taskSystem.getTasksMap());
                    continue;

                case 4:
                    // вывести список сотрудников
                    showEmployees();
                    continue;

                case 5:
                    // сохранить задачи в файл
                    taskSystem.save(taskSystem.getTasks());
                    continue;

                case 6:
                    // загрузить задачи из файла
                    taskSystem.load();
                    continue;

                case 7:
                    //изменить задачу

                    // если список задач пуст сообщаем об этом и ничего не делаем
                    if(taskSystem.getTasks().isEmpty()){
                        System.out.println(strings.emptyTaskList);
                        // иначе выбираем задачу и переходим в меню управление задачей
                    } else {
                        taskManager(chooseTask(taskSystem.getTasksMap()));
                    }
                    continue;

                case 0:
                    // закрытие потоков и выход
                    strScanner.close();
                    intScanner.close();
                    run = false;
                    break;

            }
        } while (run);

    }

    /**
     * Управление задачей.
     *
     * @param task задача.
     */
    public void taskManager(Task task){

        do {

            // вывод меню и выбранной задачи
            System.out.println(strings.chosenTask + task);
            System.out.print(strings.taskMenu);

            switch (checkInput(strings.commandRequest, Arrays.asList(1, 2, 3, 4, 5, 6, 0))) {

                case 1:
                    //назначить задачу

                    if(!(task.getStatus() == Status.Ready)) {
                        // если задача уже назначена сообщаем об этом и продолжаем
                        System.out.println(strings.taskAlreadyAssigned);
                        continue;

                    } else if(taskSystem.getEmployees().isEmpty()){
                        // если список сотрудников пуст сообщаем об этом и продолжаем
                        System.out.println(strings.emptyEmployeeList);
                        continue;

                    } else {
                        // если всё хорошо назначаем задачу выбранному сотруднику
                        taskSystem.assignTask(task, chooseEmployee());
                    }
                    continue;

                case 2:
                    //переназначить задачу

                    if(task.getStatus() == Status.Ready){
                        // если задача еще не назначена сообщаем об этом и продолжаем
                        System.out.println(strings.taskIsNotAssigned);

                    } else if(taskSystem.getEmployees().isEmpty()){
                        // если список сотрудников пуст сообщаем об этом и продолжаем
                        System.out.println(strings.emptyEmployeeList);
                        continue;
                    } else {
                        // если всё хорошо переназначаем задачу выбранному сотруднику
                        taskSystem.forwardTask(task, chooseEmployee());
                    }
                    continue;
                case 3:
                    // отмена задачи
                    taskSystem.cancelTask(task);
                    continue;
                case 4:
                    // задача выполнена
                    taskSystem.completeTask(task);
                    continue;
                case 5:
                    // задача провалена
                    taskSystem.failTask(task);
                    continue;
                case 6:
                    // главное меню
                    start();
                    continue;
                case 0:
                    // закрытие потоков и выход
                    strScanner.close();
                    intScanner.close();
                    run = false;
                    break;
            }
        } while (run);

    }

    /**
     * Добавление задачи
     */
    public void taskAdding() {

        System.out.print(strings.newTaskAdding + strings.taskNameRequest);
        // считываем название задачи
        String name = strScanner.nextLine();

        System.out.print(strings.taskDescriptionRequest);
        // считываем описание задачи
        String desc = strScanner.nextLine();

        switch (checkInput(strings.assigningRequest, Arrays.asList(1, 2))) {

            case 1:

                // если список сотрудников не пуст
                if (!taskSystem.getEmployees().isEmpty()) {

                    // предлагаем выбрать сотрудника и добавляем задачу с выбранным сотрудником
                    taskSystem.addTask(name, desc, chooseEmployee());
                    break;

                    //если список сотрудников пуст
                } else {

                    // предлагаем создать и назначить нового - 1
                    // или добавить задачу без назначения - 2
                    if (checkInput(strings.addingAndAssigningNewEmployee, Arrays.asList(1, 2)) == 1) {

                        // если выбрано создание сотрудника - создаем и назначаем
                        employeeAdding();

                        // в данном случае у нас будет только один сотрудник,
                        // поэтому просто берем первый объект из мапы
                        taskSystem.addTask(name, desc, taskSystem.getEmployeesMap().get(1));
                        break;
                    }
                }

                // создание задачи без назначения сотрудника
            case 2:
                taskSystem.addTask(name, desc);
                break;

        }
        System.out.println(strings.taskAdded);
    }

    /**
     * Добавление сотрудника.
     */
    public void employeeAdding() {

        System.out.print(strings.newEmployeeAdding + strings.employeeFirstNameRequest);
        // считываем имя
        String firstName = strScanner.nextLine();

        System.out.print(strings.employeeLastNameRequest);
        // считываем фамилию
        String lastName = strScanner.nextLine();

        //добавляем сотрудника
        taskSystem.addEmployee(firstName, lastName);
        System.out.println(strings.employeeAdded);
    }

    /**
     * Предлагает выбрать задачу из переданного списка и возвращает её.
     *
     * @param tasks Список задач для выбора.
     * @return Выбранная задача.
     */
    public Task chooseTask(Map<Integer, Task> tasks){

        showTasks(tasks);

        // считываем ID задачи
        int chosenID = checkInput(strings.chooseTask, tasks.keySet());

        // возвращаем выбранную задачу
        return taskSystem.getTasksMap().get(chosenID);

    }

    /**
     * Предлагает выбрать сотрудника из списка сотрудников и возвращает его.
     *
     * @return Выбранный сотрудник.
     */
    public Employee chooseEmployee(){

        showEmployees();

        // считываем ID сотрудника
        int chosenID = checkInput(strings.chooseEmployee, taskSystem.getEmployeesMap().keySet());

        // возвращаем выбранного сотрудника
        return taskSystem.getEmployeesMap().get(chosenID);

    }

    /**
     * Отобразить задачи из переданного списка задач.
     *
     * @param tasks Список задач.
     */
    public void showTasks(Map<Integer, Task> tasks) {

        // если задач нет
        if (taskSystem.getTasks().isEmpty()) {
            // сообщаем об этом
            System.out.println(strings.emptyTaskList);
        } else {
            // иначе выводим список задач
            System.out.println(strings.taskList);
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        }
    }

    /**
     * Отобразить всех сотрудников.
     */
    public void showEmployees() {

        // если сотрудников нет
        if (taskSystem.getEmployees().isEmpty()) {
            // сообщаем об этом
            System.out.println(strings.emptyEmployeeList);
        } else {
            // иначе выводим список задач
            System.out.println(strings.employeesList);
            for (Employee employee : taskSystem.getEmployees()) {
                System.out.println(employee);
            }
        }
    }

    /**
     * Проверка правильности ввода команды.
     *
     * @param text   текст, выводимый во время запроса команды.
     * @param values список доступных команд.
     *
     * @return команда.
     */
    public int checkInput(String text, Collection<Integer> values) {

        int command;

        // пока введенная команда не будет в списке доступных выполняем внешний цикл
        do {
            System.out.print(text);
            // пока введенная команда не будет цифрой выполняем внутренний цикл
            while (!intScanner.hasNextInt()) {
                System.out.println(strings.wrongCommand + "\n" + strings.commandRequest);
                intScanner.next();
            }
            command = intScanner.nextInt();
        } while (!values.contains(command));

        return command;
    }
}

