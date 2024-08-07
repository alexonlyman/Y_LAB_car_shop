package org.alex_group.repository.carRepo;


import org.alex_group.model.cars.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class CarRepositoryImplTest {
    private CarRepositoryImpl carRepository;
    private Car newCar;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepositoryImpl();
        newCar = new Car("Tesla", "Model 3", 2023, 50000, "USA", "White", 5);
    }

    @Test
    void givenCreatingNewCar_thenPutInMap_assertContainsNewCarInMap() {
        carRepository.createCar(newCar);
        Map<Integer, Car> allCars = carRepository.findAllCars();
        assertThat(allCars).containsValue(newCar);
    }

    @Test
    void givenMapOfAllCars_returningSizeOfMap() {
        Map<Integer, Car> allCars = carRepository.findAllCars();
        assertThat(allCars).hasSize(6);
    }

    @Test
    void givenMapOfCars_whenMapIsEmpty_assertingEmptyMap() {
        Map<Integer, Car> emptyMap = new HashMap<>(carRepository.findAllCars());
        emptyMap.clear();
        assertThat(emptyMap).isEmpty();
    }

    @Test
    void givenNonExistantId_whenFindAllCars_asserting() {
        Map<Integer, Car> allCars = carRepository.findAllCars();
        assertThat(allCars).isNotNull();
        int id = 999;
        assertThat(allCars).doesNotContainKey(id);
    }

    @Test
    void givenAddingCar_whenSearchingByArgs_thenReturnCar() {
        carRepository.findAllCars().clear();
        Car toyotaCar = new Car("Toyota", "Camry", 2022, 28000, "Japan", "Blue", 10);
        carRepository.createCar(toyotaCar);
        Map<Integer, Car> result = carRepository.findBy("Toyota", null, 30000);
        assertThat(result).isNotEmpty();

        System.out.println(result);
        Car foundCar = result.values().iterator().next();
        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getMarkName()).isEqualTo("Toyota");
        assertThat(foundCar.getPrice()).isLessThanOrEqualTo(30000);
    }

    @Test
    void givenCarAndCount_whenUsingReservingCar_thenResultMinusCar() {
        Car car = carRepository.findAllCars().values().iterator().next();
        int count = car.getCount();
        Car reservedCar = carRepository.reservation(car.getId());
        assertThat(reservedCar).isNotNull();
        assertThat(reservedCar.getCount()).isEqualTo(count - 1);
    }

    @Test
    void givenArgsToUpdateCar_whenResultIsSuccess_thenAllAssertsTrue() {
        carRepository.createCar(newCar);
        Car updatedCar = new Car(null, null, null, 35000, null, "Black", null);
        boolean result = carRepository.updateCar(newCar.getId(), updatedCar);
        assertThat(result).isTrue();
        Car foundCar = carRepository.findAllCars().get(newCar.getId());
        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getPrice()).isEqualTo(35000);
        assertThat(foundCar.getColour()).isEqualTo("Black");
    }

    @Test
    void givenCarInRepo_whenDeletingCar_thenDisappearsFromMap() {
        carRepository.createCar(newCar);
        Car car = carRepository.findAllCars().get(newCar.getId());
        carRepository.deleteCar(car.getId());
        Map<Integer,Car> allCars = carRepository.findAllCars();
        assertThat(allCars).doesNotContainValue(car);
    }
}