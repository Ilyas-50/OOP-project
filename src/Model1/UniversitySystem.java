package Model1;

import java.io.*;
import java.util.*;

public class UniversitySystem {

    private User currentUser;
    private boolean isRunning;
    private Scanner scanner;
    private DataStorage storage;

    public UniversitySystem() {
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        this.storage = DataStorage.getInstance();
    }

    public void start() {
        System.out.println("Welcome to University Management System!");
        storage.load();
        while (isRunning) {
            if (currentUser == null) {
                System.out.println("\n1. Login\n0. Exit");
                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    login();
                } else if (choice.equals("0")) {
                    isRunning = false;
                }
            }
            else{
                showMenu();
            }
        }
        storage.save();
    }

    public void showMenu() {
        System.out.println("---------------------------------------------");
        String status = currentUser.getFullStatus();
        if(currentUser instanceof Researcher) {
            System.out.println("Your status: "+ status + "Researcher");
        }
        else{
            System.out.println("Your status: "+ status);
        }

        if(currentUser instanceof Admin){
            showAdminMenu();
        }
        if(currentUser instanceof Student){
            showStudentMenu();
        }
    }

    public void showAdminMenu() {
        System.out.println("1. Add User\n2. Remove User\n3. View Logs\n4. View Users\n0. Logout");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.println("Select type: 1. Student, 2. Teacher, 3. Manager");
            String typeChoice = scanner.nextLine();

            UserType type = (typeChoice.equals("1")) ? UserType.STUDENT :
                    (typeChoice.equals("2")) ? UserType.TEACHER : UserType.MANAGER;

            User newUser = UserFactory.createUser(type);

            System.out.print("Enter First Name: ");
            newUser.setFirstName(scanner.nextLine());
            System.out.print("Enter Last Name: ");
            newUser.setLastName(scanner.nextLine());

            newUser.setLogin(newUser.getFirstName().toLowerCase().charAt(0) + "_" + newUser.getLastName().toLowerCase());
            newUser.setPassword("123"); // by default
            newUser.setId(String.valueOf(storage.getNextUserId()));

            if (newUser instanceof Student) {
                Student s = (Student) newUser;
                System.out.print("Enter Faculty (FIT, ISE, KMA): ");
                s.setFaculty(Faculty.valueOf(scanner.nextLine().toUpperCase()));
                s.setYearOfStudy(1);
            }

            //downcasting
            Admin admin = (Admin) currentUser;
            admin.addUser(newUser);
            storage.save();

            System.out.println("User created successfully! Login: " + newUser.getLogin());
        }
        else if (choice.equals("2")) {
            System.out.println("Write user's login that you want to delete: ");
            String loginToDelete = scanner.nextLine();

            User targetUser = null;
            for (User u : storage.getUsers()) {
                if (u.getLogin().equals(loginToDelete)) {
                    targetUser = u;
                    break;
                }
            }

            if (targetUser != null) {

                if (targetUser.equals(currentUser)) {
                    System.out.println("Error: You cannot delete yourself!");
                }
                else if (targetUser instanceof Admin) {
                    System.out.println("Error: You cannot delete another administrator!");
                }
                else {
                    Admin admin = (Admin) currentUser;
                    admin.removeUser(targetUser);
                    storage.save();

                    System.out.println("User '" + loginToDelete + "' deleted successfully.");
                }
            } else {
                System.out.println("User with login '" + loginToDelete + "' not found.");
            }
        }
        else if (choice.equals("3")) {
            List<String> logs = ((Admin) currentUser).viewLogs();
            if (logs.isEmpty()) {
                System.out.println("Logs are empty");
            } else {
                for (String log : logs) {
                    System.out.println(log);
                }
            }
        }
        else if (choice.equals("4")) {
            List<User> users = ((Admin) currentUser).viewUsers();
            if (users.isEmpty()) {
                System.out.println("Logs are empty");
            } else {
                for (User user : users) {
                    System.out.println(user.getLogin());
                }
            }
        }
        else if (choice.equals("0")) {
            logout();
        }
    }

    public void showStudentMenu(){

    }

    public void login() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User u : storage.getUsers()) {
            if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
                currentUser = u;
                System.out.println("Login successful! Welcome, " + u.getFirstName());
                return;
            }
        }
        System.out.println("Invalid credentials.");
    }

    public void logout() {
        String logMsg = "User " + currentUser.getLogin() + " logged out.";
        storage.addLog(logMsg);
        currentUser = null;
        storage.save();
    }

}