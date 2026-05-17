package academic;

import users.User;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a message sent between two users in the system.
 */
public class Message implements Serializable {

    private User sender;
    private User receiver;
    private String content;
    private Date date;

    /**
     * Creates a message with current timestamp.
     */
    public Message(User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return String.format("From: %s %s | %s",
                sender.getFirstName(), sender.getLastName(), content);
    }

    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public String getContent() { return content; }
    public Date getDate() { return date; }
}