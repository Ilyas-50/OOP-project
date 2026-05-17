package users;

import academic.Course;
import academic.Lesson;
import academic.Mark;
import academic.TeacherTitle;
import core.DataStorage;
import java.util.*;

/**
 * Represents a university teacher.
 * Manages courses, student marks, schedule and student ratings.
 */
public class Teacher extends Employee {

    private TeacherTitle title;
    private List<Course> courses = new ArrayList<>();
    private List<Integer> ratings = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();

    public Teacher() {
        super();
    }

    /**
     * Adds a student rating (0-10).
     */
    public void addRating(int rating) {
        if (rating < 0 || rating > 10) {
            System.out.println("Rating must be between 0 and 10.");
            return;
        }
        ratings.add(rating);
    }

    /**
     * Adds a lesson to teacher's schedule.
     */
    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    /**
     * Returns average student rating, or 0.0 if no ratings yet.
     */
    public double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) return 0.0;
        int sum = 0;
        for (int r : ratings) sum += r;
        return (double) sum / ratings.size();
    }

    /**
     * Prints all assigned courses.
     */
    public void viewCourses() {
        System.out.println("Instructor's courses:");
        for (Course c : courses) {
            System.out.println("- " + c.getCourseName());
        }
    }

    /**
     * Prints the teacher's lesson schedule.
     */
    public void viewSchedule() {
        if (lessons.isEmpty()) {
            System.out.println("No lessons scheduled.");
            return;
        }
        System.out.println("Your Schedule:");
        for (Lesson l : lessons) {
            System.out.println(l);
        }
    }

    /**
     * Creates a mark from three components and adds it to student's transcript.
     * @param att1 first attestation score
     * @param att2 second attestation score
     * @param finalExam final exam score
     */
    public void putMark(Student student, Course course, double att1, double att2, double finalExam) {
        Mark mark = new Mark(course, att1, att2, finalExam);
        student.addMark(mark);
        DataStorage.getInstance().addLog("Teacher " + this.getLastName() +
                " put mark " + mark.getTotalScore() + " to " + student.getLastName() + " for " + course.getCourseName());
    }

    public void viewStudents() {
        System.out.println("Feature to view all students is under development.");
    }

    public TeacherTitle getTitle() { return title; }
    public void setTitle(TeacherTitle title) { this.title = title; }
    public List<Course> getCourses() { return courses; }
    public void addCourse(Course course) { this.courses.add(course); }
    public List<Lesson> getLessons() { return lessons; }
    public List<Integer> getRatings() { return ratings; }
}