package Model1;

import java.util.Comparator;
import java.io.Serializable;

public class LengthComparator implements Comparator<ResearchPaper>, Serializable {
    @Override
    public int compare(ResearchPaper p1, ResearchPaper p2) {
        return Integer.compare(p2.getPages(), p1.getPages());
    }
}