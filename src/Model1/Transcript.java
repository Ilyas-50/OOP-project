package Model1;

import java.util.*;

public class Transcript implements java.io.Serializable{
    private List<Mark> marks;

    public Transcript() {
        this.marks = new ArrayList<>();
    }

    public void addMark(Mark mark) {
        this.marks.add(mark);
    }

    public boolean isCoursePassed(Course course) {
        for (Mark m : marks) {
            if (m.getCourse().getCourseName().equals(course.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (marks.isEmpty()) return "Transcript is empty.";
        StringBuilder sb = new StringBuilder("--- Academic Transcript ---\n");
        for (Mark m : marks) {
            sb.append(m.toString()).append("\n");
        }
        return sb.toString();
    }
}
