package org.alex_group.repository.orderRepo;

import org.alex_group.logging.AuditLog;
import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;
import org.alex_group.service.auth_service.AuthServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 * Implementation of the OrderRepo interface that manages a collection of buy orders and service orders.
 */
public class OrderRepoImpl implements OrderRepo {
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    private final List<BuyOrder> buyOrderList = new ArrayList<>();
    private final List<ServiceOrder> serviceOrders = new ArrayList<>();

    /**
     * Creates a new buy order and adds it to the repository.
     *
     * @param buyOrder the buy order to be added
     */
    @Override
    public void createBuyOrder(BuyOrder buyOrder) {
        buyOrderList.add(buyOrder);
        AuditLog.log("buy-order was created + " + buyOrder);
    }

    /**
     * Creates a new service order and adds it to the repository.
     *
     * @param serviceOrder the service order to be added
     */
    @Override
    public void createServiceOrder(ServiceOrder serviceOrder) {
        serviceOrders.add(serviceOrder);
    }

    /**
     * Retrieves all buy orders in the repository.
     *
     * @return a list of all buy orders
     */
    @Override
    public List<BuyOrder> findAllBuyOrders() {
        return new ArrayList<>(buyOrderList);
    }

    /**
     * Retrieves all service orders in the repository.
     *
     * @return a list of all service orders
     */
    @Override
    public List<ServiceOrder> findAllServiceOrders() {
        return serviceOrders.stream().toList();
    }

    /**
     * Finds buy orders in the repository by date, client name, status, and car ID.
     *
     * @param date       the date of the order (nullable)
     * @param clientName the name of the client (nullable)
     * @param status     the status of the order (nullable)
     * @param carId      the ID of the car (nullable)
     * @return a list of buy orders that match the criteria
     */
    @Override
    public List<BuyOrder> findOrders(LocalDateTime date, String clientName, Boolean status, Integer carId) {
        AuditLog.log("findOrdres was called " + LocalDateTime.now());
        return buyOrderList.stream()
                .filter(order -> date == null || order.getLocalDateTime().equals(date))
                .filter(order -> clientName == null || order.getUser().getFirstname().equalsIgnoreCase(clientName))
                .filter(order -> status == null || order.isApprove() == status)
                .filter(order -> carId == null || order.getCar().getId().equals(carId))
                .collect(Collectors.toList());

    }

    /**
     * Updates a service order to approved status by its ID.
     *
     * @param id the ID of the service order to be updated
     * @return true if the order was successfully updated, false otherwise
     */
    @Override
    public boolean updateCarServiceRequest(Integer id) {
        findAllBuyOrders();
        for (ServiceOrder order : serviceOrders) {
            if (order.getId().equals(id)) {
                order.setApprove(true);
            }
        }
        return false;
    }

}
