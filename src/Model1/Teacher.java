package Model1;

import java.util.*;

public class Teacher extends Employee {
    private TeacherTitle title;
    private List<Course> courses = new ArrayList<>();;

    public Teacher() {
        super();
    }

    public void viewCourses() {
        System.out.println("Instructor's courses:");
        for (Course c : courses) {
            System.out.println("- " + c.getCourseName());
        }
    }

    public void putMark(Student student, Course course, double att1, double att2, double finalExam) {
        Mark mark = new Mark(course, att1, att2, finalExam);
        student.getTranscript().addMark(mark);

        DataStorage.getInstance().addLog("Teacher " + this.getLastName() +
                " put mark " + mark.getTotalScore() + " to " + student.getLastName() + " for " + course.getCourseName());
    }

    public void viewStudents() {
        System.out.println("Feature to view all students is under development.");
    }

    public TeacherTitle getTitle() { return title; }
    public void setTitle(TeacherTitle title) { this.title = title; }

    public List<Course> getCourses() { return courses; }
    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
