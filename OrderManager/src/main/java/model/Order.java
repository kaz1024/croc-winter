package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Заказ.
 */
public class Order {

    /**
     * Идентификатор.
     */
    private Integer id;

    /**
     * Наименование.
     */
    private String title;

    /**
     * Статус оплаты.
     */
    private Boolean isPaid;

    /**
     * Дата заказа.
     */
    private LocalDate date;

    /**
     * Время заказа.
     */
    private LocalTime time;

    /**
     * Заказ.
     */
    public Order(Integer id, String title, Boolean isPaid, LocalDate date, LocalTime time) {
        this.id = id;
        this.title = title;
        this.isPaid = isPaid;
        this.date = date;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean isPaid() {
        return isPaid;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isPaid=" + isPaid +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) &&
                Objects.equals(getTitle(), order.getTitle()) &&
                Objects.equals(isPaid, order.isPaid) &&
                Objects.equals(getDate(), order.getDate()) &&
                Objects.equals(getTime(), order.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), isPaid, getDate(), getTime());
    }
}
