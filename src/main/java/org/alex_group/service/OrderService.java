package org.alex_group.service;

import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    void createBuyOrder(BuyOrder buyOrder);

    void createServiceOrder(ServiceOrder serviceOrder);


    List<BuyOrder> findOrdersBy(LocalDateTime date,String name,boolean isApprove,Integer id);

    boolean updateBuyOrderRequest(Integer id);
    boolean updateCarServiceRequest(Integer id) throws SQLException;
    List<BuyOrder> findAllBuyOrders() throws SQLException;
    List<ServiceOrder> findAllServiceOrders() throws SQLException;
}
