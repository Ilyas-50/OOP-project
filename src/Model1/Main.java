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
//            storage.save();
            System.out.println("Created initial admin: admin/admin");

            Manager manager = new Manager();
            manager.setFirstName("Manager");
            manager.setLogin("m");
            manager.setPassword("m");

            storage.getUsers().add(manager);
//            storage.save();
            System.out.println("Created initial manager: m/m");

            Teacher teacher = new Teacher();
            teacher.setFirstName("Teacher");
            teacher.setLogin("t");
            teacher.setPassword("t");

            storage.getUsers().add(teacher);
            storage.save();
            System.out.println("Created initial teacher: t/t");
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