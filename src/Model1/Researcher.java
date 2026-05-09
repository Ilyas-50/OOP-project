package Model1;

import java.util.*;

public interface Researcher {
    List<ResearchPaper> getPapers();
    void printPapers(Comparator<ResearchPaper> c);
    int calculateHIndex();
    void addPaper(ResearchPaper p);
}