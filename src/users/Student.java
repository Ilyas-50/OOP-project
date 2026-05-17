package users;

import academic.Course;
import academic.Faculty;
import academic.Mark;
import academic.Transcript;
import exceptions.CreditLimitExceededException;
import research.Researcher;
import java.util.*;

/**
 * Represents a bachelor student in the university system.
 * Handles course registration, marks, transcript and GPA tracking.
 */
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

    /**
     * Prints all currently enrolled courses.
     */
    public void viewCourses() {
        System.out.println("Enrolled Courses:");
        for (Course c : enrolledCourses) {
            System.out.println("- " + c.getCourseName());
        }
    }

    /**
     * Registers student to a course.
     * Checks prerequisites and credit limit before enrolling.
     * @throws CreditLimitExceededException if adding course exceeds 21 credits.
     */
    public void registerToCourse(Course course) throws CreditLimitExceededException {
        if (!course.getPrerequisites().isEmpty()) {
            Course required = course.getPrerequisites().getFirst();
            if (!transcript.isCoursePassed(required)) {
                System.out.println("Registration failed: You must pass " + required.getCourseName() + " first!");
                return;
            }
        }

        int MAX_CREDITS = 21;
        if (this.totalCredits + course.getCredits() > MAX_CREDITS) {
            throw new CreditLimitExceededException("Credit limit exceeded! Remaining: "
                    + (MAX_CREDITS - this.totalCredits) + ", required: " + course.getCredits());
        }

        this.enrolledCourses.add(course);
        this.totalCredits += course.getCredits();
    }

    /**
     * Adds a mark to transcript and recalculates GPA.
     */
    public void addMark(Mark mark) {
        this.transcript.addMark(mark);
        this.gpa = this.transcript.calculateGpa();
    }

    /**
     * Prints the academic transcript.
     */
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
    public int getTotalCredits() { return totalCredits; }

    public Researcher getSupervisor() { return supervisor; }
    public void setSupervisor(Researcher supervisor) { this.supervisor = supervisor; }
}