package Model1;

import java.io.*;
import java.util.*;

public class Manager extends Employee {

    public Manager() {
        super();
    }

    public void approveRegistration() {
        // TODO implement here
    }

    public void addCourse(Course course) {
        DataStorage storage = DataStorage.getInstance();
        storage.getCourses().add(course);
        storage.addLog("Manager " + this.getLastName() + " added new course: " + course.getCourseName());
        storage.save();
        System.out.println("Course " + course.getCourseName() + " successfully added to the system.");
    }

    public void viewRequests() {
        // TODO implement here
    }
}