package ru.croc.java.winter.school.zoo.tracking;

import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.TrackingEvent;
import ru.croc.java.winter.school.zoo.tracking.finder.EmployeeAndAnimalInteractionEventFinder;
import ru.croc.java.winter.school.zoo.tracking.finder.EmployeeAndEmployeeInteractionEventFinder;
import ru.croc.java.winter.school.zoo.tracking.finder.EventFinder;
import ru.croc.java.winter.school.zoo.tracking.finder.WorkAttendanceEventFinder;
import ru.croc.java.winter.school.zoo.tracking.interaction.Interaction;
import ru.croc.java.winter.school.zoo.tracking.location.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис отслеживания {@link Tracked}.
 */
public class TrackingService {
    /** Отслеживаемые объекты. ид -> объект. */
    private final Map<String, Tracked> trackable;
    /** Журнал событий. */
    private final List<TrackingEvent> events;
    /** Анализаторы событий. */
    private final List<EventFinder> eventFinders;
    /**Центральная точка нашего круглого зоопарка*/
    private final Position zooCenter = new Position(50,50);
    /** Центр окружности нашего зоопарка. */
    private double rad = 50;//не нравится, но не успел придумать по-другому

    public TrackingService() {
        trackable = new HashMap<>();
        events = new ArrayList<>();
        eventFinders = new ArrayList<>();
        eventFinders.add(new WorkAttendanceEventFinder(zooCenter, rad));
        eventFinders.add(new EmployeeAndAnimalInteractionEventFinder(3));
        eventFinders.add(new EmployeeAndEmployeeInteractionEventFinder(2));
    }

    /**
     * Добавляем новый объект для отслеживания.
     *
     * @param tracked новый объект
     */
    public void add(Tracked tracked) {
        trackable.put(tracked.getId(), tracked);
    }

    /**
     * Пришли данные с GPS-датчика(обработанные).
     *
     * @param id ид отсл. объекта
     * @param x Х
     * @param y Y
     */
    public void update(String id, double x, double y) {
        if (!trackable.containsKey(id)) {
            return;
        }

        trackable.get(id).updatePosition(x, y);
        for (EventFinder eventFinder : eventFinders) {
            events.addAll(eventFinder.findNext(trackable.get(id), trackable, events));
        }
    }

    /**
     * Снимаем слежение с объекта.
     *
     * @param tracked объект
     */
    public void remove(Tracked tracked) {
        trackable.remove(tracked.getId());
    }

    /**
     * Метод получения количества уведенных сотрудником животных.
     * @param employee Сотрудник
     * @return Количество уведенных животных.
     */
    public int getStolenAnimalsNumber(Employee employee) {
        return employee.getStolenAnimals().size();
    }

    /**
     * Метод получения общего времени проведенного сотрудником с его животными
     * @param employee Сотрудник
     * @return Время типа long
     */
    // TODO по-хорошему "статистику" вынести в отдельный класс, наверно. не успел сделать
    public long getEmployeeTotalTimeWithHisAnimals(Employee employee){
        long totalTime = 0;
        for(TrackingEvent event: events){
            //если это событие не взаимодействие сотрудника и животного - смотрим дальше
            final boolean animalEvent = event instanceof EmployeeAndAnimalInteractionEvent;
            if (!animalEvent) {
                continue;
            }
            //для удобства чтения
            Interaction currentInteraction = ((EmployeeAndAnimalInteractionEvent) event).getInteraction();
            Animal currentAnimal = (Animal)currentInteraction.getAnotherInteractionMember(employee);

            //если взаимодействие не закончено - смотрим дальше
            final boolean activeInteraction = currentInteraction.getFinishTime() != null;
            if (!activeInteraction) {
                continue;
            }
            //если во возаимодействии не учавствует нужный сотрудник - смотрим дальше
            final boolean employeeInteraction = currentInteraction.getAnotherInteractionMember(employee) != null;
            if (!employeeInteraction) {
                continue;
            }
            //если животное, учавствующее во взаимодействии - подопечное нашего сотрудника, то прибавляем время
            if (employee.getAnimals().contains(currentAnimal)) {
                totalTime += currentInteraction.getDuration();
            }
        }
        return totalTime;
    }

    public Set<Tracked> getTrackable() {
        return new HashSet<>(trackable.values());
    }

    public List<TrackingEvent> getEvents() {
        return events;
    }
}
