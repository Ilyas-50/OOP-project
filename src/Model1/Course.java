package Model1;

import java.util.*;

public class Course {

    private String courseCode;
    private String courseName;
    private int credits;
    private List<Teacher> instructors;
    private List<Course> prerequisites;
    private List<Lesson> lessons;

    public Course() {
    }

}