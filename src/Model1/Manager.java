package Model1;

import java.io.*;
import java.util.*;

public class Manager extends Employee {

    public Manager() {
        super();
    }


    public void addCourse(Course course) {
        DataStorage storage = DataStorage.getInstance();
        storage.getCourses().add(course);
        storage.addLog("Manager " + this.getLastName() + " added new course: " + course.getCourseName());
        storage.save();
        System.out.println("Course " + course.getCourseName() + " successfully added to the system.");
    }

    public void assignCourseToTeacher(Teacher teacher, Course course) {
        if (!teacher.getCourses().contains(course)) {
            teacher.addCourse(course);
            DataStorage.getInstance().addLog("Manager " + this.getLastName() + " assigned course " + course.getCourseName() + " to teacher " + teacher.getLastName());
            DataStorage.getInstance().save();
        }
    }

}