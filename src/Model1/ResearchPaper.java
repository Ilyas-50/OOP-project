package Model1;

import java.io.*;
import java.util.*;

public class ResearchPaper implements Serializable {

    private String title;
    private List<Researcher> authors;
    private String journal;
    private int pages;
    private Date date;
    private int citations;
    private String doi;

    public ResearchPaper() {
    }

    public int getCitation() {
        return citations;
    }

    public String getTitle() {
        return title;
    }

    public List<Researcher> getAuthors() {
        return authors;
    }

    public String getJournal() {
        return journal;
    }

    public int getPages() {
        return pages;
    }

    public Date getDate() {
        return date;
    }

    public String getDoi() {
        return doi;
    }
}