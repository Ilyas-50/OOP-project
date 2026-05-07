package Model1;

import java.io.*;
import java.util.*;

public class DataStorage implements java.io.Serializable {

    private static DataStorage instance;
    private List<User> users;
    private List<Course> courses;
    private List<ResearchPaper> allPapers;
    private List<String> systemLogs;
    private int userCounter = 0;

    // Singleton
    private DataStorage() {
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.allPapers = new ArrayList<>();
        this.systemLogs = new ArrayList<>();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    //Serialization
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("storage.dat"))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Deserialization
    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("storage.dat"))) {
            instance = (DataStorage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            instance = new DataStorage();
        }
    }

    public int getNextUserId() {
        return ++userCounter;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<ResearchPaper> getAllPapers() {
        return allPapers;
    }

    public List<String> getSystemLogs() {
        return systemLogs;
    }
    public void addLog(String message) {
        this.systemLogs.add(new Date() + ": " + message);
    }
}