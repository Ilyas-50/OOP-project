package Model1;

import java.util.*;

public class Teacher extends Employee {
    private TeacherTitle title;
    private List<Course> courses;
    public Lesson instructor;

    public Teacher() {
        super();
        this.courses = new ArrayList<>();
    }

    public void viewCourses() {
        System.out.println("Instructor's courses:");
        for (Course c : courses) {
            System.out.println("- " + c.getCourseName());
        }
    }

    public void putMark(Student student, Course course, Mark mark) {
        student.getTranscript().addMark(mark);
        System.out.println("Mark put for student " + (student).getLastName() + " in " + course.getCourseName());
    }

    public void viewStudents() {
        System.out.println("Feature to view all students is under development.");
    }

    public TeacherTitle getTitle() { return title; }
    public void setTitle(TeacherTitle title) { this.title = title; }

    public List<Course> getCourses() { return courses; }
}
