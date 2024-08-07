package org.alex_group.repository.orderRepo;

import org.alex_group.model.cars.Car;
import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;
import org.alex_group.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class OrderRepoImplTest {
    private OrderRepoImpl orderRepo = new OrderRepoImpl();

    private User user1;
    private User user2;
    private Car car;
    private Car car2;

    @BeforeEach
    void setUp() {
        orderRepo = new OrderRepoImpl();

        user1 = new User("Alex", "Kha", 30, "alex", "password");
        user2 = new User("Max", "Ma", 30, "max", "password123");
        car = new Car("Volkswagen", "Golf", 2021, 28000, "Germany", "Green", 5);
        car2 = new Car("Volkswagen", "Golf", 2021, 28000, "Germany", "Green", 5);

        // Явно устанавливаем ID для автомобилей
        car.setId(1);
        car2.setId(2);
    }
    @Test
    void testCreateBuyOrder() {
        BuyOrder buyOrder = new BuyOrder(LocalDateTime.now(), user1, car);
        orderRepo.createBuyOrder(buyOrder);
        List<BuyOrder> orders = orderRepo.findAllBuyOrders();
        assertThat(orders).contains(buyOrder);
    }

    @Test
    void testCreateServiceOrder() {
        ServiceOrder serviceOrder = new ServiceOrder(user2,LocalDateTime.now());
        orderRepo.createServiceOrder(serviceOrder);
        List<ServiceOrder> orders = orderRepo.findAllServiceOrders();
        assertThat(orders).contains(serviceOrder);
    }

    @Test
    void testFindAllBuyOrders() {
        BuyOrder buyOrder1 = new BuyOrder(LocalDateTime.now(), user1, car);
        BuyOrder buyOrder2 = new BuyOrder(LocalDateTime.now(), user2, car);
        orderRepo.createBuyOrder(buyOrder1);
        orderRepo.createBuyOrder(buyOrder2);
        List<BuyOrder> orders = orderRepo.findAllBuyOrders();
        assertThat(orders).containsExactly(buyOrder1, buyOrder2);
    }

    @Test
    void testFindAllServiceOrders() {
        ServiceOrder serviceOrder1 = new ServiceOrder(user1,LocalDateTime.now() );
        ServiceOrder serviceOrder2 = new ServiceOrder(user2,LocalDateTime.now());
        orderRepo.createServiceOrder(serviceOrder1);
        orderRepo.createServiceOrder(serviceOrder2);
        List<ServiceOrder> orders = orderRepo.findAllServiceOrders();
        assertThat(orders).containsExactly(serviceOrder1, serviceOrder2);
    }

    @Test
    void testFindOrders() {
        LocalDateTime date = LocalDateTime.now();
        BuyOrder buyOrder1 = new BuyOrder(date, user1, car);

        buyOrder1.setApprove(true);
        BuyOrder buyOrder2 = new BuyOrder(date.plusDays(1), user2, car2);
        buyOrder2.setApprove(false);

        orderRepo.createBuyOrder(buyOrder1);
        orderRepo.createBuyOrder(buyOrder2);

        List<BuyOrder> filteredOrders = orderRepo.findOrders(date, "Alex", true, 1);
        assertThat(filteredOrders).containsExactly(buyOrder1);

        filteredOrders = orderRepo.findOrders(null, "Max", false, null);
        assertThat(filteredOrders).containsExactly(buyOrder2);
    }

    @Test
    void testUpdateCarServiceRequest() {
        ServiceOrder serviceOrder = new ServiceOrder(user1,LocalDateTime.now());
        serviceOrder.setId(1);
        serviceOrder.setApprove(false);
        orderRepo.createServiceOrder(serviceOrder);

        boolean result = orderRepo.updateCarServiceRequest(1);
        assertThat(result).isFalse();

        List<ServiceOrder> orders = orderRepo.findAllServiceOrders();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).isApprove()).isTrue();
    }

}