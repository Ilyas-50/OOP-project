package Model1;

import java.io.*;
import java.util.*;

public interface Researcher {

    void printPapers(Comparator<ResearchPaper> c);

    int calculateHIndex();

    void addPaper(ResearchPaper p);

    boolean isActive();

}