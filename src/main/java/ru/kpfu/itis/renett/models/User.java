package ru.kpfu.itis.renett.models;

import lombok.Builder;

import java.util.Objects;

@Builder
public class User {
    private Integer id;
    private String firstName;
    private String secondName;

    private String email;
    private String login;
    private String password;

    public User(String firstName, String secondName, String email, String login, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public User(int id, String firstName, String secondName, String email, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user.id==this.id && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && email.equals(user.email) && login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, email, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
