package Model;

import java.io.*;
import java.util.*;

public class Mark implements Serializable {

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private double totalScore;
    private Course course;

    public Mark() {
    }

    public double calculateTotal() {
        return 0.0;
    }

    public String getLetterDigit() {
        return "";
    }

}