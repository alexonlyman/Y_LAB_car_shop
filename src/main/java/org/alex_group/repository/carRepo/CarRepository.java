package org.alex_group.repository.carRepo;

import org.alex_group.model.cars.Car;

import java.sql.SQLException;
import java.util.Map;


public interface CarRepository {
    Car createCar(Car car) throws SQLException;
    Map<Integer,Car > findAllCars() ;
    Map<Integer,Car> findBy(String brand, String model, Integer maxPrice);
    Car reservation(Integer id);
    boolean updateCar(Integer id, Car updatedCar);
    void deleteCar(Integer id);

}
