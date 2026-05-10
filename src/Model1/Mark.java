package Model1;

public class Mark implements java.io.Serializable {
    private double totalScore;
    private Course course;

    public Mark(Course course, double totalScore) {
        this.course = course;
        this.totalScore = totalScore;
    }

    public String getLetterDigit() {
        if (totalScore >= 95) return "A";
        if (totalScore >= 90) return "A-";
        if (totalScore >= 85) return "B+";
        if (totalScore >= 80) return "B";
        if (totalScore >= 75) return "B-";
        if (totalScore >= 70) return "C+";
        if (totalScore >= 65) return "C";
        if (totalScore >= 60) return "C-";
        if (totalScore >= 55) return "D+";
        if (totalScore >= 50) return "D";
        return "F";
    }

    public Course getCourse() { return course; }
    public double getTotalScore() { return totalScore; }

    @Override
    public String toString() {
        return String.format("%-20s | %-3s | %.1f",
                course.getCourseName(), getLetterDigit(), totalScore);
    }
}