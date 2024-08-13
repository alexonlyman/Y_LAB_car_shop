package org.alex_group.model.users;

import lombok.Data;
import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.users.roles.Roles;

import java.util.List;
/**
 * The User class represents a user within the system.
 */
@Data
public class User {
    private Integer id;
    private String firstname;
    private String lastname;
    private Integer age;
    private Roles role;
    private String password;
    private String login;
    private Boolean isAuth = false;
    private List<BuyOrder> buyOrderList;

    /**
     * Constructs a new User object with the specified parameters.
     *
     * @param firstname the first name of the user
     * @param lastname  the last name of the user
     * @param age       the age of the user
     * @param login     the login identifier for the user
     * @param password  the password for the user account
     */
    public User(String firstname, String lastname, Integer age,String login,String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.login = login;
        this.password = password;
    }


}
