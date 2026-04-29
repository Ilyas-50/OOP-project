package Model1;

import java.io.*;
import java.util.*;

public abstract class User implements Serializable {

    private String id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public User() {
    }

    public boolean login() {
        // TODO implement here
        return false;
    }

    public void logout() {
        // TODO implement here
    }

    public String toString() {
        // TODO implement here
        return "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}