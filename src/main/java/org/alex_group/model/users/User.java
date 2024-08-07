package org.alex_group.model.users;

import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.users.roles.Roles;

import java.util.List;
import java.util.Objects;
/**
 * The User class represents a user within the system.
 */
public class User {
    private static int nextId = 1;
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
        this.id = nextId++;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Roles getRole() {
        return role;
    }

    public User setRole(Roles role) {
        this.role = role;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(age, user.age) && role == user.role && Objects.equals(password, user.password) && Objects.equals(login, user.login) && Objects.equals(isAuth, user.isAuth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, age, role, password, login, isAuth);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", role=" + role +
                '}';
    }

    public Boolean getAuth() {
        return isAuth;
    }

    public User setAuth(Boolean auth) {
        isAuth = auth;
        return this;
    }

    public List<BuyOrder> getOrderList() {
        return buyOrderList;
    }

    public User setOrderList(List<BuyOrder> buyOrderList) {
        this.buyOrderList = buyOrderList;
        return this;
    }
}
