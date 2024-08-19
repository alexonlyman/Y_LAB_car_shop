package org.alex_group.service.impl;

import org.alex_group.model.cars.Car;
import org.alex_group.repository.CarRepository;
import org.alex_group.service.CarService;

import java.sql.SQLException;
import java.util.Map;

/**
 * Implementation of the CarService interface that manages car-related operations.
 */
public class CarServiceImpl implements CarService {
    private final CarRepository repository;


    /**
     * Constructs a CarServiceImpl with the given CarRepository.
     *
     * @param repository the CarRepository to be used for car operations. Must not be {@code null}.
     */
    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;

    }

    /**
     * Retrieves all cars from the repository and prints them.
     *
     * @return a map of all cars, where the key is the car ID and the value is the Car object.
     */
    @Override
    public Map<Integer, Car> findAllCars() {
        Map<Integer, Car> allCars = repository.findAllCars();
        System.out.println(allCars);
        return allCars;
    }

    /**
     * Creates a new car based on user input from a Scanner.
     */
    @Override
    public void createCar(Car car) throws SQLException {
        repository.createCar(car);
        System.out.println("Автомобиль успешно добавлен.");
    }

    /**
     * Updates an existing car based on user input from a Scanner.
     */
    @Override
    public void updateCar(Car car) {

        int id = car.getId();
        Car carToUpdate = repository.findAllCars().get(id);

        if (carToUpdate == null) {
            System.out.println("Автомобиль с ID " + id + " не найден.");
            return;
        }

        carToUpdate.setMarkName(car.getMarkName());
        carToUpdate.setModelName(car.getModelName());
        carToUpdate.setProductionYear(car.getProductionYear());
        carToUpdate.setPrice(car.getPrice());
        carToUpdate.setProductionCountry(car.getProductionCountry());
        carToUpdate.setColour(car.getColour());
        carToUpdate.setCount(car.getCount());

        repository.updateCar(id, carToUpdate);
        System.out.println("Информация об автомобиле успешно обновлена.");
        System.out.println("Обновленная информация:");
        System.out.println(carToUpdate);
    }

    /**
     * Deletes a car based on its ID from user input.
     *
     */
    @Override
    public void deleteCar(Integer id) {
        repository.deleteCar(id);
        System.out.println("Автомобиль с ID " + id + " успешно удален.");

    }

    /**
     * Finds cars based on user-provided parameters.
     *
     * @return a map of cars that match the search criteria
     */
    @Override
    public Map<Integer, Car> findCarBy(String brand, String mark, Integer maxPrice) {
        Map<Integer, Car> result = repository.findBy(brand, mark, maxPrice);

        if (result.isEmpty()) {
            System.out.println("По вашему запросу ничего не найдено.");
        } else {
            System.out.println("Найденные автомобили:");
            result.forEach((id, car) ->
                    System.out.printf("ID: %d, Car: %s%n", id, car)
            );
        }

        return result;

    }

    @Override
    public Car reservation(Integer id) {
        return repository.reservation(id);
    }
}
