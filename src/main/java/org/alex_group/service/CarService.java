package org.alex_group.service;

import org.alex_group.model.cars.Car;

import java.sql.SQLException;
import java.util.Map;

public interface CarService {
    Map<Integer, Car> findAllCars();

    void createCar(Car Car) throws SQLException;

    void updateCar(Car car);

    void deleteCar(Integer id);

    Map<Integer,Car> findCarBy(String brand,String mark,Integer maxPrice );

    Car reservation(Integer id);

}
