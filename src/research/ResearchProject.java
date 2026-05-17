package research;

import exceptions.NotAResearcherException;

import java.io.*;
import java.util.*;

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

    // По требованию: если не Researcher — бросаем исключение
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

    public void addPaper(ResearchPaper p) {
        if (p == null) return;
        if (publishedPapers.contains(p)) {
            System.out.println("Paper already added to this project.");
            return;
        }
        publishedPapers.add(p);
        System.out.println("Paper \"" + p.getTitle() + "\" added to project: " + topic);
    }

//    public Researcher getTopCitedResearcher() {
//        Researcher top = null;
//        int maxCitations = -1;
//
//        for (Researcher r : participants) {
//            int total = 0;
//            for (ResearchPaper p : r.getPapers()) {
//                total += p.getCitations();
//            }
//            if (total > maxCitations) {
//                maxCitations = total;
//                top = r;
//            }
//        }
//        return top;
//    }

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