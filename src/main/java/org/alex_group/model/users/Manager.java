package org.alex_group.model.users;

import java.util.Objects;

public class Manager extends User {
    private String position;

    public Manager(Integer id, String firstname, String lastname, Integer age,String login,String password) {
        super(firstname, lastname, age,login,password);
    }

    public String getPosition() {
        return position;
    }

    public Manager setPosition(String position) {
        this.position = position;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Manager manager = (Manager) o;
        return Objects.equals(position, manager.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), position);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "position='" + position + '\'' +
                '}';
    }
}
