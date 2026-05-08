package Model1;

import java.io.*;
import java.util.*;

public class Admin extends Employee {

    public Admin() {
        super();
    }

    // Factory
    public void createNewUser(UserType type) {
        User newUser = UserFactory.createUser(type);
        if (newUser != null) {
            addUser(newUser);
            addLog("Created new user of type: " + type);
        }
    }

    public void addUser(User u) {
        DataStorage.getInstance().getUsers().add(u);
        addLog("Admin added user: " + u.getLogin());
        DataStorage.getInstance().save();
    }

    public void removeUser(User u) {
        DataStorage.getInstance().getUsers().remove(u);
        addLog("Admin removed user: " + u.getLogin());
        DataStorage.getInstance().save();
    }

    public List<String> viewLogs() {
        return DataStorage.getInstance().getSystemLogs();
    }

    public void addLog(String message) {
        DataStorage.getInstance().addLog(message);
        DataStorage.getInstance().save();
    }

    public List<User> viewUsers(){
        return DataStorage.getInstance().getUsers();
    }
}