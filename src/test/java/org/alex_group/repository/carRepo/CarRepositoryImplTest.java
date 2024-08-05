package org.alex_group.repository.carRepo;


import org.alex_group.model.cars.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class CarRepositoryImplTest {
    private CarRepositoryImpl carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepositoryImpl();
    }

    @Test
    void testCreateCar() {
        Car newCar = new Car("Tesla", "Model 3", 2023, 50000, "USA", "White", 5);
        carRepository.createCar(newCar);
        List<Car> allCars = carRepository.findAllCars();
        assertThat(allCars).contains(newCar);
    }

    @Test
    void testFindAllCars() {
        List<Car> allCars = carRepository.findAllCars();
        assertThat(allCars).hasSize(6);
    }

    @Test
    void testFindBy() {
        List<Car> result = carRepository.findBy("Toyota", null, 30000);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMarkName()).isEqualTo("Toyota");
        assertThat(result.get(0).getPrice()).isLessThanOrEqualTo(30000);
    }

    @Test
    void testReservation() {
        Car car = carRepository.findAllCars().get(0);
        int count = car.getCount();
        Car reservedCar = carRepository.reservation(car.getId());
        assertThat(reservedCar).isNotNull();
        assertThat(reservedCar.getCount()).isEqualTo(count - 1);
    }

    @Test
    void testUpdateCar() {
        Car car = carRepository.findAllCars().get(0);
        Car updatedCar = new Car(null, null, null, 35000, null, "Black", null);
        boolean result = carRepository.updateCar(car.getId(), updatedCar);
        assertThat(result).isTrue();
        Car foundCar = carRepository.findAllCars().stream()
                .filter(c -> c.getId().equals(car.getId()))
                .findFirst()
                .orElse(null);
        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getPrice()).isEqualTo(35000);
        assertThat(foundCar.getColour()).isEqualTo("Black");
    }

    @Test
    void testDeleteCar() {
        Car car = carRepository.findAllCars().get(0);
        carRepository.deleteCar(car.getId());
        List<Car> allCars = carRepository.findAllCars();
        assertThat(allCars).doesNotContain(car);
    }
}