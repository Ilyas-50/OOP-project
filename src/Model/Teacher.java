package Model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Teacher extends Employee implements Researcher {

    /**
     * Default constructor
     */
    public Teacher() {
    }

    /**
     * 
     */
    private TeacherTitle title;

    /**
     * 
     */
    private List<Course> courses;

    /**
     * 
     */
    public Lesson instructor;



    /**
     * @return
     */
    public void viewCourses() {
        // TODO implement here
        return null;
    }

    /**
     * @param student 
     * @param course 
     * @param mark 
     * @return
     */
    public void putMark(Student student, Course course, Mark mark) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void viewStudents() {
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