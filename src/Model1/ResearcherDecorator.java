package Model1;

import java.io.*;
import java.util.*;

public class ResearcherDecorator implements Researcher {

    private User user;
    private List<ResearchPaper> papers;

    public ResearcherDecorator(User user) {
        this.user = user;
        this.papers = new ArrayList<>();
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
//        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
//        sortedPapers.sort(c);
//        for (ResearchPaper p : sortedPapers) {
//            System.out.println(p);
//        }
    }

    @Override
    public int calculateHIndex() {
        return 0;
    }

    @Override
    public void addPaper(ResearchPaper p) {
        this.papers.add(p);
    }

    @Override
    public boolean isActive() {
        return false;
    }

//    @Override
//    public boolean isActive() {
//        return !papers.isEmpty();
//    }


    public User getUser() {
        return user;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }
}