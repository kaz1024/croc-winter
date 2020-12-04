package ru.croc.java.winter.school.zoo.tracking.event;

import ru.croc.java.winter.school.zoo.tracking.interaction.Interaction;


/**
 * Событие взаимодействия сотрудника и сотрудника.
 */
public class EmployeeAndEmployeeInteractionEvent extends TrackingEvent {
    private final Interaction interaction;

    public EmployeeAndEmployeeInteractionEvent(Interaction interaction) {
        super(interaction.getStartTime());
        this.interaction = interaction;
    }

    public Interaction getInteraction() {
        return interaction;
    }
}
