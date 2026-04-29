package Model1;

import java.io.*;
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
        // TODO implement here
    }

    public void putMark(Student student, Course course, Mark mark) {
        // TODO implement here
    }

    public void viewStudents() {
        // TODO implement here
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public void setTitle(TeacherTitle title) {
        this.title = title;
    }

    public List<Course> getCourses() {
        return courses;
    }
}