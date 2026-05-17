package research;

import java.util.Comparator;
import java.io.Serializable;

public class DateComparator implements Comparator<ResearchPaper>, Serializable {
    @Override
    public int compare(ResearchPaper p1, ResearchPaper p2) {
        return p2.getDate().compareTo(p1.getDate());
    }
}