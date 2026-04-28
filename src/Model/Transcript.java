package Model;

import java.io.*;
import java.util.*;

public class Transcript implements Serializable {

    private List<Mark> marks;

    public Transcript() {
        this.marks = new ArrayList<>();
    }

}