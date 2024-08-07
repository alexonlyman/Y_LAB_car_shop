package org.alex_group.repository.carRepo;

import org.alex_group.model.cars.Car;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface CarRepository {
    void createCar(Car car);
    Map<Integer,Car > findAllCars() ;
    Map<Integer,Car> findBy(String brand, String model, Integer maxPrice);
    Car reservation(Integer id);
    boolean updateCar(Integer id, Car updatedCar);
    void deleteCar(Integer id);

}
