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
        this.transcript = new Transcript();
    }

    public void viewCourses() {
        System.out.println("Enrolled Courses:");
        for (Course c : enrolledCourses) {
            System.out.println("- " + c.getCourseName());
        }
    }

    public void registerToCourse(Course course) {
        int MAX_CREDITS = 21;
        if (this.totalCredits + course.getCredits() > MAX_CREDITS) {
            System.out.println("Error: Credit limit exceeded for " + course.getCourseName());
            return;
        }
        this.enrolledCourses.add(course);
        this.totalCredits += course.getCredits();
        System.out.println("Successfully registered to " + course.getCourseName());
    }

    public void viewTranscript() {
        System.out.println(transcript.toString());
    }

    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    public Transcript getTranscript() { return transcript; }
}
