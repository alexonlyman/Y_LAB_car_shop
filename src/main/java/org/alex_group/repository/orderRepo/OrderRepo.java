package org.alex_group.repository.orderRepo;

import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepo {
    void createBuyOrder(BuyOrder buyOrder);
    void createServiceOrder(ServiceOrder serviceOrder);
    List<BuyOrder> findAllBuyOrders() throws SQLException;
    List<ServiceOrder> findAllServiceOrders() throws SQLException;
    List<BuyOrder> findOrders(LocalDateTime date, String clientName, Boolean status, Integer carId);

    boolean updateCarServiceRequest(Integer id);
}
