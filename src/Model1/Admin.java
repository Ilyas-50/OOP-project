package Model1;

import java.io.*;
import java.util.*;

public class Admin extends Employee {
    private List<String> logs;

    public Admin() {
        super();
        this.logs = new ArrayList<>();
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
    }

    public void removeUser(User u) {
        DataStorage.getInstance().getUsers().remove(u);
        addLog("Removed user: " + u.getId());
    }

    public List<String> viewLogs() {
        return logs;
    }

    public void addLog(String message) {
        this.logs.add(new Date() + ": " + message);
    }
}