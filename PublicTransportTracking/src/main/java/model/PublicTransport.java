package model;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * Общественный транспорт.
 */
public class PublicTransport {

    /**
     * ID автомобиля
     */
    @XmlElement
    private final Integer id;

    /**
     * Тип общественного транспорта.
     */
    @XmlElement
    private final PublicTransportTypes transportType;

    /**
     * Гос. номер
     */
    @XmlElement
    private String stateNumber;

    /**
     * Номер маршрута.
     */
    @XmlElement
    private String routeNumber;

    /**
     * Название таблицы базы данных, содержащей транспорт.
     */
    private static final String TABLE_NAME = "transports";

    /**
     * Общественный транспорт.
     *
     * @param id          ID транспорта
     * @param type        тип общественного транспорта
     * @param stateNumber гос. номер
     * @param routeNumber номер маршрута
     */
    public PublicTransport(int id, PublicTransportTypes type, String stateNumber, String routeNumber) {
        this.id = id;
        this.transportType = type;
        this.stateNumber = stateNumber;
        this.routeNumber = routeNumber;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public Integer getId() {
        return id;
    }

    public PublicTransportTypes getType() {
        return transportType;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicTransport that = (PublicTransport) o;
        return Objects.equals(id, that.id) &&
                transportType == that.transportType &&
                Objects.equals(stateNumber, that.stateNumber) &&
                Objects.equals(routeNumber, that.routeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transportType, stateNumber, routeNumber);
    }

    @Override
    public String toString() {
        return "PublicTransport{" +
                "id=" + id +
                ", transportType=" + transportType +
                ", stateNumber='" + stateNumber + '\'' +
                ", routeNumber='" + routeNumber + '\'' +
                '}';
    }
}
