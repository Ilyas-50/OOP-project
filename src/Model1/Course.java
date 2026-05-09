package Model1;

import java.util.*;

public class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    private List<Course> prerequisites;

    public Course(String code, String name, int credits) {
        this.courseCode = code;
        this.courseName = name;
        this.credits = credits;
        this.prerequisites = new ArrayList<>();
    }

    public void addPrerequisite(Course course) {
        this.prerequisites.add(course);
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    
    @Override
    public String toString() { return courseName + " (" + courseCode + ")"; }
}
