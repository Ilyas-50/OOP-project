package Model1;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Admin admin = new Admin();

        //factory
        Student s = (Student) UserFactory.createUser(UserType.STUDENT);
        s.setFirstName("Ilyas");

        //decorator
        ResearcherDecorator studentResearcher = new ResearcherDecorator(s);

        // DataStorage addition
        admin.addUser((User) (Object) studentResearcher);

        // checking
        for (User u : DataStorage.getInstance().getUsers()) {
            if (u instanceof Researcher) {
                ((Researcher) u).printPapers(null);
            }
        }
    }
}