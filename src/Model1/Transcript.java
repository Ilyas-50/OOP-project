package Model1;

import java.util.*;

public class Transcript {
    private List<Mark> marks;

    public Transcript() {
        this.marks = new ArrayList<>();
    }

    public void addMark(Mark mark) {
        this.marks.add(mark);
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
