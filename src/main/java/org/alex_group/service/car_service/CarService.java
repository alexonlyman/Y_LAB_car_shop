package org.alex_group.service.car_service;

import org.alex_group.model.cars.Car;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface CarService {
    Map<Integer,Car> findAllCars();

    void createCar(Scanner scanner);

    void updateCar(Scanner scanner);

    void deleteCar(Scanner scanner);

    Map<Integer,Car> findCarBy(Scanner scanner);
}
