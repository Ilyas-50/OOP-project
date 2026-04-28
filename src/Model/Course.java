package Model;

import java.io.*;
import java.util.*;

public class Course implements Serializable {

    private String courseCode;
    private String courseName;
    private int credits;
    private List<Teacher> instructors;
    private List<Course> prerequisites;
    private List<Lesson> lessons;

    public Course() {
    }

}