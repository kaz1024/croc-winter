package ru.kaz.transport.report.system.model;

import ru.kaz.transport.report.system.jaxb.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Отметка о местоположении транспортного средства в момент времени.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationMark {

    /**
     * Название таблицы базы данных, содержащей отметки.
     */
    private static final String TABLE_NAME = "locations";

    /**
     * Транспорт, прошедший отметку.
     */
    @XmlElement(name = "vehicleInformation")
    private PublicTransport publicTransport;

    /**
     * ID транспорта, прошедшего отметку.
     */
    @XmlTransient
    private final Integer transportID;

    /**
     * Дата и время фиксации отметки.
     */
    @XmlElement(name = "markDateTime")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private final LocalDateTime localDateTime;

    /**
     * OX координата отметки.
     */
    @XmlElement(name = "OXCoordinate")
    private final Integer ox;

    /**
     * OY координата отметки.
     */
    @XmlElement(name = "OYCoordinate")
    private final Integer oy;

    /**
     * Транспорт, прошедший отметку.
     *
     * @param transportID   ID транспорта, прошедшего отметку
     * @param localDateTime дата и время фиксации отметки
     * @param ox            OX координата отметки
     * @param oy            OY координата отметки
     */
    public LocationMark(Integer transportID, LocalDateTime localDateTime, Integer ox, Integer oy) {
        this.transportID = transportID;
        this.localDateTime = localDateTime;
        this.ox = ox;
        this.oy = oy;
    }

    /**
     * Добавить транспорт в отметку местоположения.
     *
     * @param publicTransport транспорт
     */
    public void insertPublicTransport(PublicTransport publicTransport) {
        this.publicTransport = publicTransport;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Integer getOx() {
        return ox;
    }

    public Integer getOy() {
        return oy;
    }

    public Integer getTransportID() {
        return transportID;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationMark that = (LocationMark) o;
        return Objects.equals(publicTransport, that.publicTransport) &&
                Objects.equals(transportID, that.transportID) &&
                Objects.equals(localDateTime, that.localDateTime) &&
                Objects.equals(ox, that.ox) &&
                Objects.equals(oy, that.oy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicTransport, transportID, localDateTime, ox, oy);
    }

    @Override
    public String toString() {
        return "LocationMark{" +
                "publicTransport=" + publicTransport +
                ", transportID=" + transportID +
                ", localDateTime=" + localDateTime +
                ", ox=" + ox +
                ", oy=" + oy +
                '}';
    }
}
