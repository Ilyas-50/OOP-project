package users;

import core.DataStorage;
import java.util.*;

/**
 * Administrator user with full system management privileges.
 * Can manage users and view system logs.
 */
public class Admin extends Employee {

    public Admin() {
        super();
    }

    /**
     * Creates and registers a new user of the given type using Factory pattern.
     */
    public void createNewUser(UserType type) {
        User newUser = UserFactory.createUser(type);
        if (newUser != null) {
            addUser(newUser);
            addLog("Created new user of type: " + type);
        }
    }

    /**
     * Adds a user to the system and saves.
     */
    public void addUser(User u) {
        DataStorage.getInstance().getUsers().add(u);
        addLog("Admin added user: " + u.getLogin());
        DataStorage.getInstance().save();
    }

    /**
     * Removes a user from the system and saves.
     */
    public void removeUser(User u) {
        DataStorage.getInstance().getUsers().remove(u);
        addLog("Admin removed user: " + u.getLogin());
        DataStorage.getInstance().save();
    }

    /**
     * Returns all system logs.
     */
    public List<String> viewLogs() {
        return DataStorage.getInstance().getSystemLogs();
    }

    /**
     * Adds a timestamped log entry.
     */
    public void addLog(String message) {
        DataStorage.getInstance().addLog(message);
        DataStorage.getInstance().save();
    }

    /**
     * Returns all registered users.
     */
    public List<User> viewUsers() {
        return DataStorage.getInstance().getUsers();
    }
}