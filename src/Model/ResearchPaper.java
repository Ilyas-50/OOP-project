package Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ResearchPaper implements Serializable {

    /**
     * Default constructor
     */
    public ResearchPaper() {
    }

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private List<Researcher> authors;

    /**
     * 
     */
    private String journal;

    /**
     * 
     */
    private int pages;

    /**
     * 
     */
    private Date date;

    /**
     * 
     */
    private int citations;

    /**
     * 
     */
    private String doi;





    /**
     * @return
     */
    public String getCitation() {
        // TODO implement here
        return "";
    }

}