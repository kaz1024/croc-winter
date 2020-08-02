package ru.kaz.zoo;

import ru.kaz.zoo.animal.Animal;
import ru.kaz.zoo.employee.Employee;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Зоопарк.
 */
public class Zoo {
    /** Название. */
    private String title;
    /** Животные. */
    private List<Animal> animals;
    /** Сотрудники. */
    private List<Employee> employees;

    /**
     * Зоопарк.
     *
     * @param title название
     */
    public Zoo(String title) {
        this.title = title;
        animals = new ArrayList<>();
        employees = new ArrayList<>();
    }

    /**
     * Движение животного.
     * @param animal животное
     * @param x х координата
     * @param y у координата
     */
    public void move(Animal animal, double x, double y) {
        double[] coords = new double[]{x,y};
        animal.setLocation(coords);
    }

    /**
     * Движение сотрудника.
     * @param employee работник
     * @param x х координата
     * @param y у координата
     */
    public void move(Employee employee, double x, double y) {
        double[] coords = new double[]{x,y};
        //если сотрудник не на работе
        if (!employee.isWorking()){
            //и прошел через ворота, добавляем запись о приходе на работу
            if(employee.walkedThroughGates(coords)){
                return;
                //и не прошел через ворота, то вообще ничего не делаем
            } else {
                return;
            }
            //если сотрудник на работе
        } else {
            //и прошел через ворота, добавляем запись о уходе с работы
            if(employee.walkedThroughGates(coords)){
                return;
            }//и не прошел через ворота работаем дальше
        }

        //Если сотдрудник подошел к животному, делаем запись об этом
        for(Animal animal:animals){
            if (near(coords, animal.getLocation())) {
                employee.setLocation(coords, employee.getGpsID(), animal.getGpsID());
                return;
            }
        }

        for(Animal a:animals){
            //если сотрудник был рядом с животным
            if(employee.getAnimalsInContact().contains(a.getGpsID())){
                //а сейчас не рядом
                if (!near(coords, a.getLocation())){
                    //находим запись о том когда сотрудник подошел к животному
                    for(MovingRecord mV:employee.getMovingRecords()){
                        if (mV.getContactedGpsID() == a.getGpsID()){
                            //вычисляем разницу между датами в минутах
                            Duration timeInContact = Duration.between(LocalDateTime.now(), mV.getDate());
                            long minutes = Math.abs(timeInContact.toMinutes());
                            //делаем запись с длительностью контакта
                            employee.setLocation(coords, employee.getGpsID(), a.getGpsID(), minutes);
                            employee.getAnimalsInContact().remove(a.getGpsID());
                            return;
                        }
                    }
                }
            }
        }

        employee.setLocation(coords);
    }

    /**
     * Метод проверки контакта двух кого-нибудь
     * @param coords1 кто-нибудь 1
     * @param coords2 кто-нибудь 2
     * @return true, если они на расстоянии мешьше 3 метров, false иначе
     */
    public boolean near(double[] coords1, double[] coords2) {
        return Math.sqrt(Math.pow((coords2[0] - coords1[0]), 2) + Math.pow((coords2[0] - coords1[0]), 2)) < 3;
    }

    /**
     * Добавляем животное.
     *
     * @param animal животное
     * @param employee опекун
     */
    public void add(Animal animal, Employee employee) {
        if (!animals.contains(animal) && employees.contains(employee)) {
            animals.add(animal);
            employee.add(animal);
        } else {
            // TODO что делать
        }
    }

    /**
     * Добавляем сотрудника.
     *
     * @param employee сотрудник
     */
    public void add(Employee employee) {
        employees.add(employee);
    }

    public void add(Animal animal) {
        animals.add(animal);
    }

    /**
     * Добавляем сотрудника.
     *
     * @param employees сотрудники
     */
    public void add(Employee... employees) {
        for (Employee employee : employees) {
            add(employee);
        }
    }

    /**
     * Удаляем животное.
     *
     * @param animal животное
     */
    public void remove(Animal animal) {
        for (Employee employee : employees) {
            if (employee.isCare(animal)) {
                employee.remove(animal);
            }
        }
        animals.remove(animal);
    }

    /**
     * Удаление животного.
     *
     * @param employee сотрудник
     */
    public void remove(Employee employee) {
        if (employees.contains(employee) && employee.getAnimals().isEmpty()) {
            employees.remove(employee);
        } else {
            // TODO учим исключения
        }
    }


    public String getTitle() {
        return title;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return "Zoo{" +
                "title='" + title + '\'' +
                ", animals=" + animals +
                ", employees=" + employees +
                '}';
    }
}
