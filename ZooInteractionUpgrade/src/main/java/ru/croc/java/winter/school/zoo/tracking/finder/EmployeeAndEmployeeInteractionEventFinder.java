package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndEmployeeInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.TrackingEvent;
import ru.croc.java.winter.school.zoo.tracking.interaction.Interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Анализатор события {@link EmployeeAndAnimalInteractionEvent}.
 */
public class EmployeeAndEmployeeInteractionEventFinder implements EventFinder {
    /** Поисковик взаимодействий*/
    private final InteractionFinder interactionFinder;

    /**
     * Анализатор события {@link EmployeeAndAnimalInteractionEvent}.
     *
     * @param interactionDistance максимальное растояние, которое считается взаимодействием
     */
    public EmployeeAndEmployeeInteractionEventFinder(double interactionDistance) {
        this.interactionFinder = new InteractionFinder(interactionDistance);
    }

    @Override
    public List<? extends TrackingEvent> findNext(Tracked updatedTracked,
                                                  Map<String, Tracked> trackable, List<TrackingEvent> events) {
        //Формируем список для найденных событий
        final List<EmployeeAndEmployeeInteractionEvent> newEvents = new ArrayList<>();
        for (Tracked trackedA : trackable.values()) {
            //Если нужный объект имеет местоположение и не совпадает с рассматриваем продолжаем
            if (trackedA == updatedTracked || trackedA.getCurrentLocation() == null) {
                continue;
            }

            //Если взаимодействуют сотрудник с сотрудником продолжаем
            final boolean employeeAndEmployee = trackedA instanceof Employee && updatedTracked instanceof Employee;

            if (!employeeAndEmployee) {
                continue;
            }

            //находим взаимодействие между объектами
            final Interaction interaction = interactionFinder.findInteraction(trackedA, updatedTracked);

            //если у них было взаимодействие продолжаем
            boolean nullInteraction = interaction == null;
            if (nullInteraction){
                continue;
            }

            //создаем новое событие и добавляем в наш список
            final EmployeeAndEmployeeInteractionEvent event = new EmployeeAndEmployeeInteractionEvent(interaction);
            newEvents.add(event);

        }
        return newEvents;
    }
}
