package org.alex_group.service.impl;

import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;
import org.alex_group.model.users.User;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.repository.OrderRepo;
import org.alex_group.service.OrderService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the OrderService interface that manages order-related operations.
 */
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    User currentUser = UserContext.getCurrentUser();
    LocalDateTime dateTime = LocalDateTime.now();

    /**
     * Constructs an OrderServiceImpl with the given OrderRepo and CarRepository.
     *
     * @param orderRepo the repository for managing orders
     *
     */
    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    /**
     * Creates a new buy order based on user input from a Scanner.
     *
     *
     */
    @Override
    public void createBuyOrder(BuyOrder buyOrder) {
        orderRepo.createBuyOrder(buyOrder);
    }

    /**
     * Creates a new service order based on user input from a Scanner.
     **/
    @Override
    public void createServiceOrder(ServiceOrder serviceOrder) {
        orderRepo.createServiceOrder(serviceOrder);
    }

    /**
     * Processes an application based on user input from a Scanner.
     *
     * @return true if the application was successfully processed, false otherwise
     */
    @Override
    public boolean updateBuyOrderRequest(Integer id) {
       return orderRepo.updateBuyOrderRequest(id);
    }

    /**
     * Finds orders based on user-provided parameters.
     *
     * @return a list of orders that match the search criteria
     */
    @Override
    public List<BuyOrder> findOrdersBy(LocalDateTime date,String name,boolean isApprove,Integer id) {
        return orderRepo.findOrdersBy(date, name, isApprove, id);
    }

    /**
     * Updates the status of a car service request based on user input from a Scanner.
     *
     */
    @Override
    public boolean updateCarServiceRequest(Integer id) throws SQLException {
        return orderRepo.updateCarServiceRequest(id);
    }

    /**
     * Retrieves all buy orders from the repository and prints them.
     *
     * @return a list of all buy orders
     */
    @Override
    public List<BuyOrder> findAllBuyOrders() throws SQLException {
        return orderRepo.findAllBuyOrders();
    }

    /**
     * Retrieves all service orders from the repository and prints them.
     *
     * @return a list of all service orders
     */
    @Override
    public List<ServiceOrder> findAllServiceOrders() throws SQLException {
        return orderRepo.findAllServiceOrders();

    }


}
