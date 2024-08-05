package org.alex_group.service.car_service;

import org.alex_group.model.cars.Car;

import java.util.List;
import java.util.Scanner;

public interface CarService {
    List<Car> findAllCars();

    void createCar(Scanner scanner);

    void updateCar(Scanner scanner);

    void deleteCar(Scanner scanner);

    List<Car> findCarBy(Scanner scanner);
}
