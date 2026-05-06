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

//    public void registerToCourse(Course course) throws CreditLimitExceededException {
//        int MAX_CREDITS = 21;
//        if (this.totalCredits + course.getCredits() > MAX_CREDITS) {
//            throw new CreditLimitExceededException(
//                    "Cannot register for " + course.getCourseName() +
//                            ". Credit limit exceeded! Current: " + totalCredits +
//                            ", Course: " + course.getCredits()
//            );
//        }
//        // Если лимит не превышен, добавляем курс
//        this.enrolledCourses.add(course);
//        this.totalCredits += course.getCredits();
//        System.out.println("Successfully registered to " + course.getCourseName());
//    }

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