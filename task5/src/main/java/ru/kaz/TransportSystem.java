package ru.kaz;

import ru.kaz.transport.Vehicle;
import ru.kaz.transport.car.Car;
import ru.kaz.transport.car.PassengerCar;
import ru.kaz.transport.indmobility.IndividualMobility;
import ru.kaz.transport.publictransport.PublicTransport;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Транспортная система.
 */
public class TransportSystem {

    /**Название города куда внедрена система*/
    private String cityName;

    /**Список всего траспорта в системе*/
    List<Vehicle> vehicles;

    /**
     * Транспортная система
     * @param cityName
     */
    public TransportSystem(String cityName) {
        this.cityName = cityName;
        vehicles = new ArrayList<>();
    }

    /**
     * Добавляем автомобиль.
     * @param vehicle
     */
    public void registration(Vehicle... vehicle){
        vehicles.addAll(Arrays.asList(vehicle));

    }

    /**
     * Метод получения числа автомобилей выпущенных в год n.
     * @param year
     * @return
     */
    public int passCarsNum(int year) {
        int num = 0;
        for (Vehicle v:vehicles) {
            if(v instanceof PassengerCar) {
                if(((PassengerCar) v).getYearOfManufacture() == year){
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * Метод получения общей вместимости общественного траспорта.
     * @return
     */
    public int totalCapacity() {
        int cap = 0;
        for(Vehicle v:vehicles) {
            if(v instanceof PublicTransport) {
                cap += ((PublicTransport) v).getCapacity();
            }
        }
        return cap;
    }

    /**
     * Метод получения информации обо всех автомобилях.
     * @return
     */
    public List<String> carDisplayInfo() {
        List<String> all = new ArrayList<>();
        for (Vehicle v:vehicles){
            if(v instanceof Car){
                all.add(v.toString());
            }
        }
        return all;
    }
    /**
     * Метод получения информации обо всем общественном траспорте.
     * @return
     */
    public List<String> PublicTransportDisplayInfo() {
        List<String> all = new ArrayList<>();
        for (Vehicle v:vehicles){
            if(v instanceof PublicTransport){
                all.add(v.toString());
            }
        }
        return all;
    }

    /**
     * Метод получения информации обо всех средствах индивидуальной мобильности.
     * @return
     */
    public List<String> IndividualMobilityDisplayInfo() {
        List<String> all = new ArrayList<>();
        for (Vehicle v:vehicles){
            if(v instanceof IndividualMobility){
                all.add(v.toString());
            }
        }
        return all;
    }

    @Override
    public String toString() {
        return "TransportSystem{" +
                "cityName='" + cityName + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }

    public String getCityName() {
        return cityName;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
