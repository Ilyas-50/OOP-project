package Model1;

import java.io.*;
import java.util.*;

public class MessageSystem {

    private List<Message> messages;

    public MessageSystem() {
        this.messages = new ArrayList<>();
    }

    public void sendMessage(User sender, User receiver, String text) {
        // TODO implement here
    }

    public List<Message> getUnreadMessages(User u) {
        // TODO implement here
        return null;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}