package Model1;

import java.io.*;
import java.util.*;

public class Student extends User {

    private int yearOfStudy;
    private int totalCredits;
    private double gpa;
    private Faculty faculty;
    private Researcher supervisor;
    private List<Course> enrolledCourses;
    private Transcript transcript;

    public Student() {
        super();
        this.enrolledCourses = new ArrayList<>();
    }

    public void viewCourses() {
        // TODO implement here
    }

    public void registerToCourse(Course course) {
        // TODO implement here
    }

    public void viewTranscript() {
        // TODO implement here
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
}