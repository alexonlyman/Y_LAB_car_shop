package org.alex_group.model.order;

import lombok.Data;
import org.alex_group.model.cars.Car;
import org.alex_group.model.users.User;

import java.time.LocalDateTime;
/**
 * The BuyOrder class represents an order for buying a car.
 */
@Data
public class BuyOrder {
    private Integer id;
    private LocalDateTime localDateTime;
    private User user;
    private Car car;
    private boolean approve = false;

    /**
     * Constructs a new BuyOrder object with the specified parameters.
     *
     * @param localDateTime the date and time when the order was created
     * @param user          the user who made the order
     * @param car           the car being ordered
     */
    public BuyOrder(LocalDateTime localDateTime, User user, Car car) {

        this.localDateTime = localDateTime;
        this.user = user;
        this.car = car;
    }

}
