package Model1;

import java.io.*;
import java.util.*;

//public class Main {
//    public static void main(String[] args) {
//        Admin admin = new Admin();
//
//        //factory
//        Student s = (Student) UserFactory.createUser(UserType.STUDENT);
//        s.setFirstName("Ilyas");
//
//        //decorator
//        ResearcherDecorator studentResearcher = new ResearcherDecorator(s);
//
//        // DataStorage addition
//        admin.addUser((User) (Object) studentResearcher);
//
//        // checking
//        for (User u : DataStorage.getInstance().getUsers()) {
//            if (u instanceof Researcher) {
//                ((Researcher) u).printPapers(null);
//            }
//        }
//    }
//}


public class Main {
    public static void main(String[] args) {
        DataStorage storage = DataStorage.getInstance();

        storage.load();

        System.out.println("Users in database: " + storage.getUsers().size());


        if (storage.getUsers().isEmpty()) {
            Admin root = new Admin();
            root.setFirstName("Admin");
            root.setLogin("admin");
            root.setPassword("admin");

            storage.getUsers().add(root);
            storage.save();
            System.out.println("Created initial admin: admin/admin");
        }

        UniversitySystem system = new UniversitySystem();
        system.start();
    }
}
