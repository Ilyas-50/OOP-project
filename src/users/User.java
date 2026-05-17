package users;

import research.ResearcherDecorator;
import java.io.Serializable;

/**
 * Abstract base class for all users in the university system.
 * Holds common identity fields and optional researcher role via Decorator pattern.
 */
public abstract class User implements Serializable {

    private String id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private ResearcherDecorator researcherData; // null if not a researcher

    public User() {}

    /**
     * Creates a user with full credentials.
     */
    public User(String id, String login, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public boolean login() { return false; }
    public void logout() {}

    /**
     * Returns the simple class name as the user's role status.
     */
    public String getFullStatus() {
        return this.getClass().getSimpleName();
    }

    /**
     * Returns researcher decorator if user has researcher role, null otherwise.
     */
    public ResearcherDecorator getResearcherData() { return researcherData; }
    public void setResearcherData(ResearcherDecorator researcherData) { this.researcherData = researcherData; }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", name=" + firstName + " " + lastName + "]";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}