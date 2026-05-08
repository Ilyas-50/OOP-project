package Model1;

import java.io.*;
import java.util.*;

public interface Researcher {

//    public void printPapers(Comparator<ResearchPaper> c) {
//        // 1. Берем список статей (например, из DataStorage или из поля объекта)
//        List<ResearchPaper> myPapers = new ArrayList<>(this.papers);
//
//        myPapers.sort(c);
//
//        // 3. Печатаем результат
//        System.out.println("--- Sorted Research Papers ---");
//        for (ResearchPaper p : myPapers) {
//            System.out.println(p.getCitationInFormat());
//        }
//    }

//    public default int calculateHIndex() {
//        // 1. Получаем список статей исследователя
//        List<ResearchPaper> papers = getPapers(); // Предположим, метод возвращает List<ResearchPaper>
//        if (papers == null || papers.isEmpty()) {
//            return 0;
//        }
//
//        // 2. Сортируем статьи по убыванию цитирований (используем твой компаратор)
//        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
//        sortedPapers.sort(new CitationsComparator());
//
//        int hIndex = 0;
//        // 3. Алгоритм подсчета:
//        for (int i = 0; i < sortedPapers.size(); i++) {
//            int citations = sortedPapers.get(i).getCitations();
//            int position = i + 1; // Позиция статьи в списке (начиная с 1)
//
//            if (citations >= position) {
//                hIndex = position;
//            } else {
//                // Как только количество цитат меньше порядкового номера, дальше проверять нет смысла
//                break;
//            }
//        }
//
//        return hIndex;
//    }
    void addPaper(ResearchPaper p);

    boolean isActive();

}