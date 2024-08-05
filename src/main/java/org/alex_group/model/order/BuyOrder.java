package org.alex_group.model.order;

import org.alex_group.model.cars.Car;
import org.alex_group.model.users.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class BuyOrder {
    private static int nextId = 1;
    private Integer id;
    private LocalDateTime localDateTime;
    private User user;
    private Car car;
    private boolean approve = false;

    public BuyOrder(LocalDateTime localDateTime, User user, Car car) {
        this.id = nextId++;
        this.localDateTime = localDateTime;
        this.user = user;
        this.car = car;
    }

    public Integer getId() {
        return id;
    }

    public BuyOrder setId(Integer id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public BuyOrder setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BuyOrder setUser(User user) {
        this.user = user;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public BuyOrder setCar(Car car) {
        this.car = car;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyOrder buyOrder = (BuyOrder) o;
        return Objects.equals(id, buyOrder.id) && Objects.equals(localDateTime, buyOrder.localDateTime) && Objects.equals(user, buyOrder.user) && Objects.equals(car, buyOrder.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localDateTime, user, car);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", localDateTime=" + localDateTime +
                ", user=" + user +
                ", car=" + car +
                '}';
    }

    public boolean isApprove() {
        return approve;
    }

    public BuyOrder setApprove(boolean approve) {
        this.approve = approve;
        return this;
    }
}
