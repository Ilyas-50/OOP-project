
import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class User implements Serializable {

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String login;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String firstName;

    /**
     * 
     */
    private String lastName;

    /**
     * 
     */
    private String email;




    /**
     * @return
     */
    public boolean login() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public void logout() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String toString() {
        // TODO implement here
        return "";
    }

}