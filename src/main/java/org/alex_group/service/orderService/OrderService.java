package org.alex_group.service.orderService;

import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public interface OrderService {
    void createBuyOrder(Scanner scanner);

    void createServiceOrder(Scanner scanner);

    boolean applicationProcessing(Scanner scanner) throws SQLException;

    List<BuyOrder> findOrdersBy(Scanner scanner);
    void updateCarServiceRequest(Scanner scanner) throws SQLException;
    List<BuyOrder> findAllBuyOrders() throws SQLException;
    List<ServiceOrder> findAllServiceOrders() throws SQLException;
}
