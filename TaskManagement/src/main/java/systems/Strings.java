package systems;

/**
 * "Ресурсный" класс для хранения строк.
 */
public class Strings {

    String delimiter = "--------------------------------------------\n";

    String mainMenu = delimiter +
            "1 - добавить задачу.\n" +
            "2 - добавить сотрудника.\n" +
            "3 - посмотреть список задач.\n" +
            "4 - посмотреть список работников.\n" +
            "5 - сохранить задачи.\n" +
            "6 - загрузить задачи.\n" +
            "7 - изменить задачу.\n" +
            "0 - выход.\n" +
            delimiter;

    String taskMenu = delimiter +
            "1 - назначить задачу.\n" +
            "2 - перенаправить задачу.\n" +
            "3 - отменить задачу.\n" +
            "4 - завершить задачу.\n" +
            "5 - задача провалена.\n" +
            "6 - главное меню.\n" +
            "0 - выход.\n" +
            delimiter;

    String commandRequest = "Введите команду: ";

    String wrongCommand = "Неверная команда!";

    String newTaskAdding = delimiter + "Добавление новой задачи.\n" + delimiter;

    String taskNameRequest = "Введите название задачи: ";

    String taskDescriptionRequest = "Введите описание задачи: ";

    String newEmployeeAdding = delimiter + "Добавление нового сотрудника.\n" + delimiter;

    String employeeFirstNameRequest = "Введите имя сотрудника: ";

    String employeeLastNameRequest = "Введите фамилию сотрудника: ";

    String emptyEmployeeList = "Список сотрудников пуст!";

    String emptyTaskList = "Список задач пуст!";

    String taskList = "Список задач:";

    String employeesList = "Список сотрудников:";

    String addingAndAssigningNewEmployee = emptyEmployeeList + "\n" +
            "1 - добавить и назначить нового содрудника.\n" +
            "2 - создать задачу без назначения.\n";

    String assigningRequest = "Назначить сотрудника?\n" +
            "1 - да.\n" +
            "2 - нет.\n";

    String taskAdded = delimiter + "Задача успешно добавлена.\n" + delimiter;

    String employeeAdded = delimiter + "Сотрудник успешно добавлен!\n" + delimiter;

    String chooseTask = "Выберите ID задачи:\n";

    String chooseEmployee = "Выберите ID сотрудника:\n";

    String taskAlreadyAssigned = "Задача уже назначена!";

    String taskIsNotAssigned = "Задача еще не назначена!";

    String chosenTask = "Выбранная задача:\n";


}
