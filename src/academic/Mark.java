package academic;

public class Mark implements java.io.Serializable {

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private double totalScore;
    private Course course;

    public Mark(Course course, double firstAttestation, double secondAttestation, double finalExam) {
        this.course = course;
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
        this.totalScore = firstAttestation + secondAttestation + finalExam;
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
    public double getFirstAttestation() { return firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public double getFinalExam() { return finalExam; }

    @Override
    public String toString() {
        return String.format("%-20s | ATT1: %-5.1f | ATT2: %-5.1f | FINAL: %-5.1f | Total: %-5.1f | %s",
                course.getCourseName(), firstAttestation, secondAttestation, finalExam, totalScore, getLetterDigit());
    }
}