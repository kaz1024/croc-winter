package ru.kaz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kaz.transport.car.PassengerCar;
import ru.kaz.transport.car.Truck;
import ru.kaz.transport.publictransport.Bus;
import ru.kaz.transport.publictransport.Metro;
import ru.kaz.transport.publictransport.Tram;
import ru.kaz.transport.publictransport.TrolleyBus;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Транспортная система.
 */
public class TransportSystemTest {
    private TransportSystem transportSystem;

    @BeforeEach
    public void init() {
        transportSystem = new TransportSystem("Краснодар");
    }

    @DisplayName("Тест теста")
    @Test
    public void testTest() {

        /**Добавляем машины*/
        PassengerCar porsche = new PassengerCar("Porsche", 1963);
        PassengerCar dodge = new PassengerCar("Dodge", 1963);
        PassengerCar vaz = new PassengerCar("Десятка", 1967);
        Truck ural = new Truck("Ural");

        /**Регистрируем*/
        transportSystem.registration(ural, porsche, vaz, dodge);

        /**
         * Проверка функции регистрации автомобиля.
         */
        Assertions.assertEquals(new HashSet<>(Arrays.asList(ural, porsche, vaz, dodge)), new HashSet<>(transportSystem.getVehicles()));

        Bus pazik = new Bus("ПАЗ", 40);
        Metro metro = new Metro("81-717/714", 330);
        Tram tram = new Tram("Витязь", 260);
        TrolleyBus troll = new TrolleyBus("МТРЗ", 110);

        transportSystem.registration(pazik, metro, tram, troll);

        /**
         * Проверка функции подсчета количества автомобилей, выпущеных n лет назад
         */
        Assertions.assertEquals(2, transportSystem.passCarsNum(1963));

        /**
         * Проверка функции подсчета максимального количества пассажиров
         */
        Assertions.assertEquals(740, transportSystem.totalCapacity());

        /**
         * Вывод информации о заданном транспортном средстве.
         */
        System.out.println(porsche);

        /**
         * Вывод информации обо всех машинах одного типа.
         */
        System.out.println((transportSystem.carDisplayInfo()));
    }


}
