package ru.kaz.transport.report.system.dto;

import ru.kaz.transport.report.system.model.LocationMark;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data transfer object для конвертации списка транспорта в XML.
 */
@XmlRootElement(name = "publicTransportInRadius")
public class LocationMarkDTO implements DTOCollection {

    /**
     * Список отметок местоположения.
     */
    @XmlElement(name = "vehicle")
    private final List<LocationMark> locationMarks;

    /**
     * Data transfer object для конвертации списка транспорта в XML.
     */
    public LocationMarkDTO() {
        this.locationMarks = new ArrayList<>();
    }

    /**
     * Data transfer object для конвертации списка транспорта в XML.
     *
     * @param locationMarks список транспорта
     */
    public LocationMarkDTO(List<LocationMark> locationMarks) {
        this.locationMarks = locationMarks;
    }

    /**
     * Data transfer object для конвертации списка транспорта в XML.
     *
     * @param locationMarks VarArg транаспорта
     */
    public LocationMarkDTO(LocationMark... locationMarks) {
        this.locationMarks = new ArrayList<>();
        this.locationMarks.addAll(Arrays.asList(locationMarks));
    }

    @Override
    public String toString() {
        return "LocationMarkList{" +
                "locationMarks=" + locationMarks +
                '}';
    }
}
