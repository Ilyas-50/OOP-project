package academic;

import java.util.*;

public class Course implements java.io.Serializable{
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

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course c = (Course) o;
        return courseCode.equals(c.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }

    @Override
    public String toString() { return courseName + " (" + courseCode + ")"; }
}
