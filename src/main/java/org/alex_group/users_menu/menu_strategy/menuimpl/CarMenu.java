package org.alex_group.users_menu.menu_strategy.menuimpl;

import org.alex_group.model.cars.Car;
import org.alex_group.service.CarService;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class CarMenu {
    private final CarService carService;

    public CarMenu(CarService carService) {
        this.carService = carService;
    }

    public void createCar(Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите марку автомобиля:");
        String markName = scanner.nextLine();
        System.out.println("Введите модель автомобиля:");
        String modelName = scanner.nextLine();
        System.out.println("Введите год производства:");
        int productionYear = scanner.nextInt();
        System.out.println("Введите цену:");
        int price = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите страну производства:");
        String productionCountry = scanner.nextLine();
        System.out.println("Введите цвет автомобиля:");
        String colour = scanner.nextLine();
        System.out.println("Введите количество автомобилей:");
        int count = scanner.nextInt();
        Car newCar = new Car(markName, modelName, productionYear, price, productionCountry, colour, count);
        carService.createCar(newCar);
        System.out.println("Автомобиль успешно добавлен.");
    }

    public void updateCar(Scanner scanner) {
        System.out.println("Введите ID автомобиля для обновления:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Car carToUpdate = null;
        for (Map.Entry<Integer, Car> entry : carService.findAllCars().entrySet()) {
            if (entry.getKey() == id) {
                carToUpdate = entry.getValue();
            }
        }

        if (carToUpdate == null) {
            System.out.println("Автомобиль с ID " + id + " не найден.");
            return;
        }

        System.out.println("Текущая информация об автомобиле:");
        System.out.println(carToUpdate);

        System.out.println("Введите новую марку автомобиля (или Enter для пропуска):");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            carToUpdate.setMarkName(input);
        }

        System.out.println("Введите новую модель автомобиля (или Enter для пропуска):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            carToUpdate.setModelName(input);
        }

        System.out.println("Введите новый год производства (или 0 для пропуска):");
        int year = scanner.nextInt();
        if (year != 0) {
            carToUpdate.setProductionYear(year);
        }
        scanner.nextLine();

        System.out.println("Введите новую цену (или 0 для пропуска):");
        int price = scanner.nextInt();
        if (price != 0) {
            carToUpdate.setPrice(price);
        }
        scanner.nextLine();

        System.out.println("Введите новую страну производства (или Enter для пропуска):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            carToUpdate.setProductionCountry(input);
        }

        System.out.println("Введите новый цвет автомобиля (или Enter для пропуска):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            carToUpdate.setColour(input);
        }

        System.out.println("Введите новое количество (или 0 для пропуска):");
        int count = scanner.nextInt();
        if (count != 0) {
            carToUpdate.setCount(count);
        }
        scanner.nextLine();

        System.out.println("Информация об автомобиле успешно обновлена.");
        System.out.println("Обновленная информация:");
        carService.updateCar(carToUpdate);
        System.out.println(carToUpdate);
    }

    public void deleteCar(Scanner scanner) {
        System.out.println("Введите ID автомобиля для обновления:");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        carService.deleteCar(id);
    }

    public Map<Integer, Car> findCarBy(Scanner scanner) {
        System.out.println("Для поиска автомобиля по параметрам введите следующую информацию:");
        scanner.nextLine();
        System.out.println("Бренд (или нажмите Enter для пропуска): ");
        String brand = scanner.nextLine().trim();
        if (brand.isEmpty()) brand = null;

        System.out.println("Марка (или нажмите Enter для пропуска): ");
        String mark = scanner.nextLine().trim();
        if (mark.isEmpty()) mark = null;

        Integer maxPrice = null;
        while (true) {
            System.out.print("Максимальная цена (или нажмите Enter для пропуска): ");
            String priceInput = scanner.nextLine().trim();
            if (priceInput.isEmpty()) {
                break;
            }
            try {
                maxPrice = Integer.parseInt(priceInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число для цены или нажмите Enter для пропуска.");
            }
        }

        Map<Integer, Car> result = carService.findCarBy(brand, mark, maxPrice);

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
}