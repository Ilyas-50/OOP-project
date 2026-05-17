package core;

import academic.Course;
import academic.MessageSystem;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import users.User;
import java.io.*;
import java.util.*;

/**
 * Central data storage for the university system.
 * Implements Singleton pattern and handles serialization/deserialization.
 * Stores all users, courses, papers, projects, messages and logs.
 */
public class DataStorage implements java.io.Serializable {

    private static DataStorage instance;
    private List<User> users;
    private List<Course> courses;
    private List<ResearchPaper> allPapers;
    private List<String> systemLogs;
    private int userCounter = 0;
    private List<ResearchProject> allProjects;
    private MessageSystem messageSystem = new MessageSystem();

    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "storage.dat";

    private DataStorage() {
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.allPapers = new ArrayList<>();
        this.systemLogs = new ArrayList<>();
        this.allProjects = new ArrayList<>();
    }

    /**
     * Returns the single DataStorage instance, creating it if necessary.
     */
    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    /**
     * Serializes current state to storage.dat file.
     */
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes data from storage.dat into current instance.
     * Silently skips if file does not exist.
     */
    public void load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            DataStorage loadedData = (DataStorage) ois.readObject();
            this.users = (loadedData.users != null) ? loadedData.users : new ArrayList<>();
            this.courses = (loadedData.courses != null) ? loadedData.courses : new ArrayList<>();
            this.allPapers = (loadedData.allPapers != null) ? loadedData.allPapers : new ArrayList<>();
            this.systemLogs = (loadedData.systemLogs != null) ? loadedData.systemLogs : new ArrayList<>();
            this.allProjects = (loadedData.allProjects != null) ? loadedData.allProjects : new ArrayList<>();
            this.messageSystem = (loadedData.messageSystem != null) ? loadedData.messageSystem : new MessageSystem();
            this.userCounter = loadedData.userCounter;
        } catch (Exception e) {
            System.out.println("No existing data found or error loading.");
        }
    }

    /** Returns next unique user ID. */
    public int getNextUserId() { return ++userCounter; }

    /** Adds a timestamped entry to system logs. */
    public void addLog(String message) {
        this.systemLogs.add(new Date() + ": " + message);
    }

    /**
     * Returns all research projects where the given researcher participates.
     */
    public List<ResearchProject> getProjectsForResearcher(Researcher res) {
        List<ResearchProject> myProjects = new ArrayList<>();
        for (ResearchProject p : allProjects) {
            if (p.getParticipants().contains(res)) {
                myProjects.add(p);
            }
        }
        return myProjects;
    }

    /**
     * Finds and returns a user by login, or null if not found.
     */
    public User getUserByLogin(String login) {
        for (User u : users) {
            if (u.getLogin().equals(login)) return u;
        }
        return null;
    }

    /**
     * Registers a new research paper globally and logs the publication.
     */
    public void registerNewPaper(ResearchPaper p, String authorLastName) {
        this.allPapers.add(p);
        this.addLog("Researcher " + authorLastName + " published: " + p.getTitle());
        this.save();
    }

    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }
    public List<ResearchPaper> getAllPapers() { return allPapers; }
    public List<String> getSystemLogs() { return systemLogs; }
    public MessageSystem getMessageSystem() { return messageSystem; }
    public List<ResearchProject> getAllProjects() {
        if (this.allProjects == null) this.allProjects = new ArrayList<>();
        return this.allProjects;
    }
}