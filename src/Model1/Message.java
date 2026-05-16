package Model1;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable {

    private User sender;
    private User receiver;
    private String content;
    private Date date;
    private boolean isRead;

    public Message(User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.date = new Date();
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public String getContent() { return content; }
    public Date getDate() { return date; }
    public boolean isRead() { return isRead; }

    @Override
    public String toString() {
        return String.format("[%s] From: %s %s | %s",
                isRead ? "READ" : "NEW",
                sender.getFirstName(), sender.getLastName(),
                content);
    }
}