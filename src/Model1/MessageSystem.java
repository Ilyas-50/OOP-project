package Model1;

import java.io.Serializable;
import java.util.*;

public class MessageSystem implements Serializable {

    private List<Message> messages;

    public MessageSystem() {
        this.messages = new ArrayList<>();
    }

    public void sendMessage(User sender, User receiver, String text) {
        Message msg = new Message(sender, receiver, text);
        messages.add(msg);
        DataStorage.getInstance().addLog("Message from " + sender.getLastName()
                + " to " + receiver.getLastName() + ": " + text);
        DataStorage.getInstance().save();
        System.out.println("Message sent to " + receiver.getFirstName() + " " + receiver.getLastName());
    }

    public List<Message> getUnreadMessages(User u) {
        List<Message> unread = new ArrayList<>();
        for (Message m : messages) {
            if (m.getReceiver().getLogin().equals(u.getLogin()) && !m.isRead()) {
                unread.add(m);
            }
        }
        return unread;
    }

    public List<Message> getInbox(User u) {
        List<Message> inbox = new ArrayList<>();
        for (Message m : messages) {
            if (m.getReceiver().getLogin().equals(u.getLogin())) {
                inbox.add(m);
            }
        }
        return inbox;
    }

    public List<Message> getSent(User u) {
        List<Message> sent = new ArrayList<>();
        for (Message m : messages) {
            if (m.getSender().getLogin().equals(u.getLogin())) {
                sent.add(m);
            }
        }
        return sent;
    }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
}