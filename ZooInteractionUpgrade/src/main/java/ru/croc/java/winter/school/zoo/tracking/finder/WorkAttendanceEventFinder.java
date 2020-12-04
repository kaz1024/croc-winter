package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.employee.Shift;
import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.TrackingEvent;
import ru.croc.java.winter.school.zoo.tracking.event.WorkAttendanceEvent;
import ru.croc.java.winter.school.zoo.tracking.interaction.Interaction;
import ru.croc.java.winter.school.zoo.tracking.location.Position;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Анализатор события {@link WorkAttendanceEvent}.
 */
public class WorkAttendanceEventFinder implements EventFinder {
    /** Список незавершенных смен для всех сотрудников. */
    private final Map<String, Shift> shifts = new HashMap<>();
    /** Центр зоопарка. */
    private final Position zooCenter;
    /** Радиус зоопарка. */
    private double rad;

    public WorkAttendanceEventFinder(Position zooCenter, double rad) {
        this.zooCenter = zooCenter;
        this.rad = rad;
    }

    @Override
    public List<? extends TrackingEvent> findNext(Tracked updatedTracked,
                                                  Map<String, Tracked> trackable, List<TrackingEvent> events) {
        // TODO список всегда содержит 1 элемент из-за реализуемого метода
        //наверно так не очень хорошо делать, по другому не успел придумать
        final List<WorkAttendanceEvent> workingShift = new ArrayList<>();
        //если отслеживаемый объект не сотрудник - выходим
        if (!(updatedTracked instanceof Employee)) {
            return workingShift;
        }
        //Если он не на смене(не работает)
        // TODO не уверен стоит ли разбивать такой if или такое еще допустимо
        if (!onShift(updatedTracked)) {
            //и не на территории - ничего не делаем
            if (!isOnTerritory(updatedTracked.getCurrentLocation().position)){
                return workingShift;
            }
            //если на территории - создаем событие прихода на работу
            final Shift shift = new Shift(updatedTracked, LocalDateTime.now());
            startShift(shift);
            workingShift.add(new WorkAttendanceEvent(shift));
            return  workingShift;
        }

        //если он на смене(работает) и не выходил - ничего не делаем
        if (isOnTerritory(updatedTracked.getCurrentLocation().position)){
            return workingShift;
        }
        //Если был на работе и вышел
        //Проверяем не украл ли зверюшку
        theftCheck(updatedTracked, events);

        //Добавялем в смену время её завершения.
        final Shift shift = getCurrentShift(updatedTracked);
        shift.setFinishTime(LocalDateTime.now());
        finishShift(shift);

        // TODO не нравится обилие return workingShift, хотя может так и надо
        return workingShift;
    }

    /**
     * Проверяем прошел ли сотрудник через ворота
     * @param position позиция сотрудника
     * @return true если прошел, false иначе
     */
    public boolean isOnTerritory(Position position) {
        return Math.pow((position.x - zooCenter.x),2) + Math.pow((position.y - zooCenter.y),2) <= Math.pow(rad,2);
    }
    /**
     * Проверяем работает ли сотрудник.
     * @param employee сотрудник
     * @return true работает, false иначе
     */
    public boolean onShift(Tracked employee) {
        return getCurrentShift(employee) != null;
    }

    /**
     * Находим незаконченную смену работника.
     * @param employee работник
     * @return текущая смена
     */
    public Shift getCurrentShift(Tracked employee) {
        return shifts.get(employee.getId());
    }

    /**
     * Добавляем смену для отслеживаний её завершения.
     * @param shift Новая смена.
     */
    public void startShift(Shift shift) {
        shifts.put(shift.getEmployee().getId(), shift);
    }

    /**
     * Удаляем законченную смену для прекращения отслеживания.
     * @param shift Текущая смена.
     */
    public void finishShift(Shift shift){
        shifts.remove(shift.getEmployee().getId());
    }

    /**
     * Проверка сотрудника на вывод животного при уходе с работы
     * @param employee Сотрудник.
     * @param events Список событий.
     */
    public void theftCheck(Tracked employee, List<TrackingEvent> events) {
        //смотрим по всем событиям
        for(TrackingEvent event:events) {
            boolean isAnimalEvent = event instanceof EmployeeAndAnimalInteractionEvent;
            //если событие - не взаимодействие животного и сотрудника - выходим
            if(!isAnimalEvent){
                continue;
            }
            //для читаемости
            Interaction currentInteraction = ((EmployeeAndAnimalInteractionEvent) event).getInteraction();

            //если проверяемый сотрудник не учавствовал во взаимодействии или взаимодействие закончено - выходим
            if (currentInteraction.getAnotherInteractionMember(employee) == null ||
                    !(currentInteraction.getFinishTime() == null)) {
                return;
            }
            // иначе записываем украденное животное в список украденных животных сотрудника
            ((Employee) employee).addStolenAnimal(currentInteraction.getAnotherInteractionMember(employee).getId());
        }
    }
}
