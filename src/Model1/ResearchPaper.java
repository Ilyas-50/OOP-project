package Model1;

import java.util.*;
import java.io.Serializable;

public class ResearchPaper implements Serializable {

    private String title;
    private List<Researcher> authors;
    private String journal;
    private int pages;
    private Date date;
    private int citations;
    private String doi;

    public ResearchPaper(String title, List<Researcher> authors, String journal, int pages, Date date, String doi) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.date = date;
        this.doi = doi;
        this.citations = 0;
    }

//    public String getCitationInFormat() {
//        StringBuilder authorsList = new StringBuilder();
//        for (Researcher r : authors) {
//            authorsList.append(((User)r).getLastName()).append(" "); // Каст к User, чтобы взять фамилию
//        }
//        return String.format("%s. \"%s\". %s, %d. DOI: %s",
//                authorsList.toString().trim(), title, journal, pages, doi);
//    }

    public void addCitation() {
        this.citations++;
    }

    public int getCitations() {
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

    @Override
    public String toString() {
        return "Paper: '" + title + "' | Journal: " + journal + " | Citations: " + citations;
    }
}