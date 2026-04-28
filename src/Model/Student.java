package Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Student extends User implements Researcher {

    /**
     * Default constructor
     */
    public Student() {
    }

    /**
     * 
     */
    private int yearOfStudy;

    /**
     * 
     */
    private int totalCredits;

    /**
     * 
     */
    private double gpa;

    /**
     * 
     */
    private Faculty faculty;

    /**
     * 
     */
    private Researcher supervisor;

    /**
     * 
     */
    private List<Course> enrolledCourses;

    /**
     * 
     */
    private Transcript transcript;





    /**
     * @return
     */
    public void viewCourses() {
        // TODO implement here
        return null;
    }

    /**
     * @param course 
     * @return
     */
    public void registerToCourse(Course course) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void viewTranscript() {
        // TODO implement here
        return null;
    }

    /**
     * @param c 
     * @return
     */
    public void printPapers(Comparator c) {
        // TODO implement Researcher.printPapers() here
        return null;
    }

    /**
     * @return
     */
    public int calculateHIndex() {
        // TODO implement Researcher.calculateHIndex() here
        return 0;
    }

    /**
     * @param p 
     * @return
     */
    public void addPaper(ResearchPaper p) {
        // TODO implement Researcher.addPaper() here
        return null;
    }

    /**
     * @return
     */
    public boolean isActive() {
        // TODO implement Researcher.isActive() here
        return false;
    }

}