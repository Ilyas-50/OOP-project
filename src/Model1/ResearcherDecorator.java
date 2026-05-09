package Model1;

import java.io.Serializable;
import java.util.*;

public class ResearcherDecorator implements Researcher, Serializable {

    private User user;
    private List<ResearchPaper> papers;

    public ResearcherDecorator(User user) {
        this.user = user;
        this.papers = new ArrayList<>();
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return this.papers;
    }

    @Override
    public void addPaper(ResearchPaper p) {
        this.papers.add(p);
        DataStorage.getInstance().getAllPapers().add(p);
        String logMsg = String.format("Researcher %s added a new paper: '%s'", user.getLastName(), p.getTitle());
        DataStorage.getInstance().addLog(logMsg);
        DataStorage.getInstance().save();
    }

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

    @Override
    public int calculateHIndex() {
        if (papers == null || papers.isEmpty()) return 0;
        List<ResearchPaper> sorted = new ArrayList<>(this.papers);
        sorted.sort(new CitationsComparator());

        int hIndex = 0;
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getCitations() >= (i + 1)) {
                hIndex = i + 1;
            } else {
                break;
            }
        }
        return hIndex;
    }

    public User getUser() {
        return user;
    }
}