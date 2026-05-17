package users;

import academic.Course;
import academic.Lesson;
import academic.Mark;
import academic.TeacherTitle;
import core.DataStorage;

import java.util.*;

public class Teacher extends Employee {
    private TeacherTitle title;
    private List<Course> courses = new ArrayList<>();
    private List<Integer> ratings = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();

    public Teacher() {
        super();
    }

    public void addRating(int rating) {
        if (rating < 0 || rating > 10) {
            System.out.println("Rating must be between 0 and 10.");
            return;
        }
        ratings.add(rating);
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) return 0.0;
        int sum = 0;
        for (int r : ratings) sum += r;
        return (double) sum / ratings.size();
    }

    public void viewCourses() {
        System.out.println("Instructor's courses:");
        for (Course c : courses) {
            System.out.println("- " + c.getCourseName());
        }
    }

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
    public List<Integer> getRatings() { return ratings; }
}