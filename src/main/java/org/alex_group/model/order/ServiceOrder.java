package org.alex_group.model.order;

import org.alex_group.model.users.User;

import java.time.LocalDateTime;
import java.util.Objects;
/**
 * The ServiceOrder class represents an order for a service.
 */
public class ServiceOrder {
    private static int nextId = 1;
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
    public ServiceOrder( User user, LocalDateTime localDateTime) {
        this.id = nextId++;
        this.user = user;
        this.localDateTime = localDateTime;
    }

    public Integer getId() {
        return id;
    }

    public ServiceOrder setId(Integer id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public ServiceOrder setUser(User user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public ServiceOrder setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceOrder that = (ServiceOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, localDateTime);
    }

    @Override
    public String toString() {
        return "ServiceOrder{" +
                "id=" + id +
                ", user=" + user +
                ", localDateTime=" + localDateTime +
                '}';
    }

    public boolean isApprove() {
        return approve;
    }

    public ServiceOrder setApprove(boolean approve) {
        this.approve = approve;
        return this;
    }
}
