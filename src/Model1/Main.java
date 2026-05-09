package Model1;

import java.io.*;
import java.util.*;

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


////decorator
//ResearcherDecorator studentResearcher = new ResearcherDecorator(s);
//
//// DataStorage addition
//admin.addUser((User) (Object) studentResearcher);