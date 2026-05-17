package research;

import core.DataStorage;
import exceptions.NotAResearcherException;
import users.User;
import java.io.Serializable;
import java.util.*;

/**
 * Decorator that adds Researcher functionality to any User.
 * Implements Decorator design pattern.
 */
public class ResearcherDecorator implements Researcher, Serializable {

    private User user;
    private List<ResearchPaper> papers;

    /**
     * Wraps a user with researcher capabilities.
     */
    public ResearcherDecorator(User user) {
        this.user = user;
        this.papers = new ArrayList<>();
    }

    @Override
    public List<ResearchPaper> getPapers() { return this.papers; }

    /**
     * Adds paper to personal list and registers it globally in DataStorage.
     */
    @Override
    public void addPaper(ResearchPaper p) {
        this.papers.add(p);
        DataStorage.getInstance().registerNewPaper(p, user.getLastName());
    }

    /**
     * Prints papers sorted by given comparator.
     */
    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        if (papers.isEmpty()) {
            System.out.println("No papers found for this researcher.");
            return;
        }
        List<ResearchPaper> sortedPapers = new ArrayList<>(this.papers);
        sortedPapers.sort(c);
        System.out.println("--- Research Papers for " + user.getLastName() + " ---");
        for (ResearchPaper p : sortedPapers) {
            System.out.println(p.getCitationInFormat());
        }
    }

    /**
     * Creates a new ResearchProject with this researcher as first participant.
     */
    public void createProject(String topic) {
        ResearchProject project = new ResearchProject(topic);
        try {
            project.addParticipant(this);
            DataStorage.getInstance().getAllProjects().add(project);
            DataStorage.getInstance().addLog("Project created: " + topic + " by " + user.getLastName());
            DataStorage.getInstance().save();
        } catch (NotAResearcherException e) {
            System.err.println("Critical error: " + e.getMessage());
        }
    }

    /**
     * Returns all projects this researcher participates in.
     */
    public List<ResearchProject> getMyProjects() {
        return DataStorage.getInstance().getProjectsForResearcher(this);
    }

    /**
     * Calculates H-index based on citation counts of all papers.
     */
    @Override
    public int calculateHIndex() {
        if (papers == null || papers.isEmpty()) return 0;
        List<ResearchPaper> sorted = new ArrayList<>(this.papers);
        sorted.sort(new CitationsComparator());
        int hIndex = 0;
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getCitations() >= (i + 1)) hIndex = i + 1;
            else break;
        }
        return hIndex;
    }

    public User getUser() { return user; }
}