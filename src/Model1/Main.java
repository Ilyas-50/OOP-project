package Model1;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DataStorage storage = DataStorage.getInstance();
        storage.load();

        System.out.println("Users in database: " + storage.getUsers().size());

        if (storage.getUsers().isEmpty()) {

            // --- COURSES ---
            Course oop = new Course("CS101", "Object-Oriented Programming", 5);
            Course math = new Course("MATH101", "Calculus", 4);
            Course algo = new Course("CS201", "Algorithms", 5);
            Course db = new Course("CS202", "Databases", 4);
            Course ai = new Course("CS301", "Artificial Intelligence", 3);

            algo.addPrerequisite(oop);   // Algorithms требует OOP
            ai.addPrerequisite(algo);    // AI требует Algorithms

            storage.getCourses().addAll(Arrays.asList(oop, math, algo, db, ai));

            // --- ADMIN ---
            Admin root = new Admin();
            root.setFirstName("Admin");
            root.setLastName("Root");
            root.setLogin("admin");
            root.setPassword("admin");
            root.setId(String.valueOf(storage.getNextUserId()));
            storage.getUsers().add(root);
            System.out.println("Created admin: admin/admin");

            // --- MANAGER ---
            Manager managerOR = new Manager();
            managerOR.setFirstName("John");
            managerOR.setLastName("Smith");
            managerOR.setLogin("m");
            managerOR.setPassword("m");
            managerOR.setId(String.valueOf(storage.getNextUserId()));
            managerOR.setManagerType(ManagerType.OR);
            storage.getUsers().add(managerOR);
            System.out.println("Created OR manager: m/m");

            Manager managerDept = new Manager();
            managerDept.setFirstName("Anna");
            managerDept.setLastName("Johnson");
            managerDept.setLogin("m2");
            managerDept.setPassword("m2");
            managerDept.setId(String.valueOf(storage.getNextUserId()));
            managerDept.setManagerType(ManagerType.DEPARTAMENT);
            storage.getUsers().add(managerDept);
            System.out.println("Created Department manager: m2/m2");

            // --- TEACHERS ---
            Teacher teacher1 = new Teacher();
            teacher1.setFirstName("Alice");
            teacher1.setLastName("Smith");
            teacher1.setLogin("t");
            teacher1.setPassword("t");
            teacher1.setId(String.valueOf(storage.getNextUserId()));
            teacher1.setTitle(TeacherTitle.PROFESSOR);
            teacher1.addCourse(oop);
            teacher1.addCourse(algo);
            storage.getUsers().add(teacher1);
            System.out.println("Created teacher: t/t");

            Teacher teacher2 = new Teacher();
            teacher2.setFirstName("Bob");
            teacher2.setLastName("Jones");
            teacher2.setLogin("t2");
            teacher2.setPassword("t2");
            teacher2.setId(String.valueOf(storage.getNextUserId()));
            teacher2.setTitle(TeacherTitle.SENIOR_LECTOR);
            teacher2.addCourse(math);
            teacher2.addCourse(db);
            storage.getUsers().add(teacher2);
            System.out.println("Created teacher: t2/t2");

            // --- STUDENTS ---
            Student student1 = new Student();
            student1.setFirstName("Ivan");
            student1.setLastName("Petrov");
            student1.setLogin("s1");
            student1.setPassword("s1");
            student1.setId(String.valueOf(storage.getNextUserId()));
            student1.setFaculty(Faculty.FIT);
            student1.setYearOfStudy(1);
            try { student1.registerToCourse(oop); } catch (CreditLimitExceededException e) { System.out.println(e.getMessage()); }
            try { student1.registerToCourse(math); } catch (CreditLimitExceededException e) { System.out.println(e.getMessage()); }
            storage.getUsers().add(student1);
            System.out.println("Created student: s1/s1");

            Student student2 = new Student();
            student2.setFirstName("Maria");
            student2.setLastName("Ivanova");
            student2.setLogin("s2");
            student2.setPassword("s2");
            student2.setId(String.valueOf(storage.getNextUserId()));
            student2.setFaculty(Faculty.ISE);
            student2.setYearOfStudy(2);
            try { student2.registerToCourse(oop); } catch (CreditLimitExceededException e) { System.out.println(e.getMessage()); }
            try { student2.registerToCourse(db); } catch (CreditLimitExceededException e) { System.out.println(e.getMessage()); }
            storage.getUsers().add(student2);
            System.out.println("Created student: s2/s2");

            Student student3 = new Student();
            student3.setFirstName("Dias");
            student3.setLastName("Akhmetov");
            student3.setLogin("s3");
            student3.setPassword("s3");
            student3.setId(String.valueOf(storage.getNextUserId()));
            student3.setFaculty(Faculty.KMA);
            student3.setYearOfStudy(3);
            // s3 уже прошёл OOP — добавляем оценку в транскрипт
            student3.getTranscript().addMark(new Mark(oop, 28, 27, 38));
            try { student3.registerToCourse(algo); } catch (CreditLimitExceededException e) { System.out.println(e.getMessage()); }
            storage.getUsers().add(student3);
            System.out.println("Created student: s3/s3");

            // --- RESEARCHER (teacher1 — профессор, значит всегда researcher) ---
            ResearcherDecorator rd = new ResearcherDecorator(teacher1);
            teacher1.setResearcherData(rd);

            ResearchPaper paper = new ResearchPaper(
                    "Deep Learning in OOP Systems",
                    Arrays.asList((Researcher) rd),
                    "IEEE Journal",
                    12,
                    new Date(),
                    "10.1109/example.2024"
            );
            rd.addPaper(paper);

            storage.save();
            System.out.println("\nInitial data created and saved.");
        }

        UniversitySystem system = new UniversitySystem();
        system.start();
    }
}