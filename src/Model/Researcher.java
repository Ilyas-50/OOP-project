package Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface Researcher {





    /**
     * @param c 
     * @return
     */
    public void printPapers(Comparator c);

    /**
     * @return
     */
    public int calculateHIndex();

    /**
     * @param p 
     * @return
     */
    public void addPaper(ResearchPaper p);

    /**
     * @return
     */
    public boolean isActive();

}