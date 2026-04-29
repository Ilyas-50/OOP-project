package Model1;

import java.io.*;
import java.util.*;

public class ResearcherDecorator implements Researcher {

    private User user;
    private List<ResearchPaper> papers;

    public ResearcherDecorator() {
    }

    public ResearcherDecorator(User user) {
        this.user = user;
        this.papers = new ArrayList<>();
    }

    public void printPapers(Comparator<ResearchPaper> c) {
        // TODO implement here
    }

    public int calculateHIndex() {
        // TODO implement here
        return 0;
    }

    public void addPaper(ResearchPaper p) {
        // TODO implement here
    }

    public boolean isActive() {
        // TODO implement here
        return false;
    }

}