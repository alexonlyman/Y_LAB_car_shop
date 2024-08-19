package org.alex_group.model.order;

import lombok.Data;
import org.alex_group.model.users.User;

import java.time.LocalDateTime;
/**
 * The ServiceOrder class represents an order for a service.
 */
@Data
public class ServiceOrder {
    private Integer id;
    private User user;
    private LocalDateTime localDateTime;
    private boolean approve;

    /**
     * Constructs a new ServiceOrder object with the specified parameters.
     *
     * @param user          the user who made the service order
     * @param localDateTime the date and time when the service order was created
     */
    public ServiceOrder(User user, LocalDateTime localDateTime) {
        this.user = user;
        this.localDateTime = localDateTime;
    }

}
