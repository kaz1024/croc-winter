package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.TrackingEvent;
import ru.croc.java.winter.school.zoo.tracking.interaction.Interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Анализатор события {@link EmployeeAndAnimalInteractionEvent}.
 */
public class EmployeeAndAnimalInteractionEventFinder implements EventFinder {

    private final InteractionFinder interactionFinder;

    /**
     * Анализатор события {@link EmployeeAndAnimalInteractionEvent}.
     *
     * @param interactionDistance максимальное растояние, которое считается взаимодействием
     */
    public EmployeeAndAnimalInteractionEventFinder(double interactionDistance) {
        this.interactionFinder = new InteractionFinder(interactionDistance);
    }

    @Override
    public List<? extends TrackingEvent> findNext(Tracked updatedTracked,
                                                  Map<String, Tracked> trackable, List<TrackingEvent> events) {
        //Формируем список для найденных событий
        final List<EmployeeAndAnimalInteractionEvent> newEvents = new ArrayList<>();
        for (Tracked trackedA : trackable.values()) {
            //Если нужный объект имеет местоположение и не совпадает с рассматриваем продолжаем
            if (trackedA == updatedTracked || trackedA.getCurrentLocation() == null) {
                continue;
            }

            //Если взаимодействуют сотрудник с животным продолжаем
            final boolean animalAndEmployee = trackedA instanceof Animal && updatedTracked instanceof Employee
                    || trackedA instanceof Employee && updatedTracked instanceof Animal;
            if (!animalAndEmployee) {
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
            final EmployeeAndAnimalInteractionEvent event = new EmployeeAndAnimalInteractionEvent(interaction);
            newEvents.add(event);

        }
        return newEvents;
    }
}
