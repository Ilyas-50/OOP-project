package research;

import exceptions.NotAResearcherException;
import java.io.*;
import java.util.*;

/**
 * Represents a university research project.
 * Groups researchers and their published papers under a common topic.
 */
public class ResearchProject implements Serializable {

    private String topic;
    private List<ResearchPaper> publishedPapers;
    private List<Researcher> participants;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public ResearchProject() {
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    /**
     * Adds a researcher as participant.
     * @throws NotAResearcherException if researcher is null.
     */
    public void addParticipant(Researcher r) throws NotAResearcherException {
        if (r == null) {
            throw new NotAResearcherException("Cannot add null as a participant.");
        }
        if (participants.contains(r)) {
            System.out.println("This researcher is already a participant.");
            return;
        }
        participants.add(r);
        System.out.println("Participant added to project: " + topic);
    }

    /**
     * Adds a paper to the project if not already present.
     */
    public void addPaper(ResearchPaper p) {
        if (p == null) return;
        if (publishedPapers.contains(p)) {
            System.out.println("Paper already added to this project.");
            return;
        }
        publishedPapers.add(p);
        System.out.println("Paper \"" + p.getTitle() + "\" added to project: " + topic);
    }

    @Override
    public String toString() {
        return "ResearchProject{topic='" + topic + "', participants=" + participants.size()
                + ", papers=" + publishedPapers.size() + "}";
    }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public List<ResearchPaper> getPublishedPapers() { return publishedPapers; }
    public List<Researcher> getParticipants() { return participants; }
}