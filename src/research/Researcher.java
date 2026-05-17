package research;

import java.util.*;

/**
 * Interface representing a researcher in the university system.
 * Can be implemented directly or applied via Decorator pattern.
 */
public interface Researcher {

    /** Returns all research papers by this researcher. */
    List<ResearchPaper> getPapers();

    /**
     * Prints papers sorted by the given comparator.
     * @param c comparator (by citations, date or length)
     */
    void printPapers(Comparator<ResearchPaper> c);

    /** Calculates and returns the H-index based on citation counts. */
    int calculateHIndex();

    /** Adds a research paper to this researcher's list. */
    void addPaper(ResearchPaper p);
}