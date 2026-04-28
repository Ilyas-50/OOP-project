package Model;

import java.io.*;
import java.util.*;

public class DataStorage implements Serializable {

    private static DataStorage instance;
    private List<User> users;
    private List<Course> courses;
    private List<ResearchPaper> allPapers;

    public DataStorage() {
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

    public void save() {
    }

    public void load() {
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