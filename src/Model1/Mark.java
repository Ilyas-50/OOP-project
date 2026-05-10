package Model1;

public class Mark implements java.io.Serializable{
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private double totalScore;
    private Course course;

    public Mark() {}

    public Mark(double firstAtt, double secondAtt, double finalExam, Course course) {
        this.firstAttestation = firstAtt;
        this.secondAttestation = secondAtt;
        this.finalExam = finalExam;
        this.course = course;
        this.totalScore = calculateTotal();
    }

    public double calculateTotal() {
        this.totalScore = firstAttestation + secondAttestation + finalExam;
        return totalScore;
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

    @Override
    public String toString() {
        return course.getCourseName() + ": " + getLetterDigit() + " (" + totalScore + ")";
    }
}
