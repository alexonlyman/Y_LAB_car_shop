package org.alex_group.service.orderService;

import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;

import java.util.List;
import java.util.Scanner;

public interface OrderService {
    void createBuyOrder(Scanner scanner);

    void createServiceOrder(Scanner scanner);

    boolean applicationProcessing(Scanner scanner);

    List<BuyOrder> findOrdersBy(Scanner scanner);
    void updateCarServiceRequest(Scanner scanner);
    List<BuyOrder> findAllBuyOrders();
    List<ServiceOrder> findAllServiceOrders();
}
