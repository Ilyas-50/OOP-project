package research;

import java.util.Comparator;
import java.io.Serializable;

public class CitationsComparator implements Comparator<ResearchPaper>, Serializable {
    @Override
    public int compare(ResearchPaper p1, ResearchPaper p2) {
        return Integer.compare(p2.getCitations(), p1.getCitations());
    }
}