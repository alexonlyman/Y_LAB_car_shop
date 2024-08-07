package org.alex_group.repository.carRepo;
import org.alex_group.logging.AuditLog;
import org.alex_group.model.cars.Car;

import java.util.*;
/**
 * Implementation of the CarRepository interface that manages a collection of cars.
 */
public class CarRepositoryImpl implements CarRepository {
    private final Map<Integer, Car> cars = new HashMap<>();

    public CarRepositoryImpl() {
        List<Car> carList = Arrays.asList(
                new Car("Toyota", "Camry", 2022, 30000, "Japan", "Silver", 5),
                new Car("Honda", "Civic", 2021, 25000, "Japan", "Blue", 5),
                new Car("Ford", "Mustang", 2023, 45000, "USA", "Red", 5),
                new Car("BMW", "X5", 2022, 60000, "Germany", "Black", 5),
                new Car("Mercedes-Benz", "C-Class", 2023, 55000, "Germany", "White", 5),
                new Car("Volkswagen", "Golf", 2021, 28000, "Germany", "Green", 5)
        );
        for (Car car : carList) {
            cars.put(car.getId(), car);
        }
    }

    /**
     * Adds a new car to the repository.
     *
     * @param car the car to be added
     */
    @Override
    public void createCar(Car car) {
        cars.put(car.getId(), car);
    }

    /**
     * Retrieves all cars in the repository.
     *
     * @return a map of all cars
     */
    @Override
    public Map <Integer,Car > findAllCars() {
        return new HashMap<>(cars);
    }

    /**
     * Finds cars in the repository by brand, model, and maximum price.
     *
     * @param brand    the brand of the car (nullable)
     * @param model    the model of the car (nullable)
     * @param maxPrice the maximum price of the car (nullable)
     * @return a map of cars that match the criteria
     */
    @Override
    public Map<Integer,Car> findBy(String brand, String model, Integer maxPrice) {
        Map<Integer, Car> result = new HashMap<>();
        for (Map.Entry<Integer,Car> entry : findAllCars().entrySet() ) {
            if ((brand == null || entry.getValue().getMarkName().equalsIgnoreCase(brand)) &&
                    (model == null || entry.getValue().getModelName().equalsIgnoreCase(model)) &&
                    (maxPrice == null || entry.getValue().getPrice() <= maxPrice)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * Reserves a car by its ID, reducing its count by 1 if available.
     *
     * @param id the ID of the car to be reserved
     * @return the reserved car or null if not available or not found
     */
    @Override
    public Car reservation(Integer id) {
        Car car = cars.get(id);
        if (car.getId() != null && car.getCount() > 0) {
            car.setCount(car.getCount() - 1);
            return car;
        }
        return null;
    }

    /**
     * Updates the details of a car in the repository.
     *
     * @param id         the ID of the car to be updated
     * @param updatedCar the car with updated details
     * @return true if the car was successfully updated, false otherwise
     */
    @Override
    public boolean updateCar(Integer id, Car updatedCar) {
            Car car = cars.get(id);
        if (car == null) {
            return false;
        }
            if (car.getId().equals(id)) {
                if (updatedCar.getMarkName() != null) {
                    car.setMarkName(updatedCar.getMarkName());
                }
                if (updatedCar.getModelName() != null) {
                    car.setModelName(updatedCar.getModelName());
                }
                if (updatedCar.getProductionYear() != null) {
                    car.setProductionYear(updatedCar.getProductionYear());
                }
                if (updatedCar.getPrice() != null) {
                    car.setPrice(updatedCar.getPrice());
                }
                if (updatedCar.getProductionCountry() != null) {
                    car.setProductionCountry(updatedCar.getProductionCountry());
                }
                if (updatedCar.getColour() != null) {
                    car.setColour(updatedCar.getColour());
                }
                if (updatedCar.getCount() != null) {
                    car.setCount(updatedCar.getCount());
                }
                AuditLog.log("car with id " + id + "was updated with " + updatedCar);
                return true;
        }
        return false;
    }
    /**
     * Deletes a car from the repository by its ID.
     *
     * @param id the ID of the car to be deleted
     */
    @Override
    public void deleteCar(Integer id) {
        cars.remove(id);
    }

}
