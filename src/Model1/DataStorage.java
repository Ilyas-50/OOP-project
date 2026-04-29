package Model1;

import java.io.*;
import java.util.*;

public class DataStorage implements java.io.Serializable {

    private static DataStorage instance;
    private List<User> users;
    private List<Course> courses;
    private List<ResearchPaper> allPapers;

    private DataStorage() { // Singleton
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.allPapers = new ArrayList<>();
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

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<ResearchPaper> getAllPapers() {
        return allPapers;
    }
}