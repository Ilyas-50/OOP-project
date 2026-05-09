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

        String researcherPrefix = (currentUser.getResearcherData() != null) ? "Researcher " : "";
        System.out.println("Your status: " + researcherPrefix + status);

        if (currentUser instanceof Admin) {
            showAdminMenu();
        } else if (currentUser instanceof Student) {
            showStudentMenu();
        }

//        if (currentUser.getResearcherData() != null) {
//            System.out.println("R. Researcher Menu");
            // Добавим обработку в конкретные меню или вынесем отдельно
//        }
    }

    public void showAdminMenu() {
        System.out.println("1. Add User\n2. Remove User\n3. View Logs\n4. View Users\n5. Make someone Researcher\n0. Logout");
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
        else if (choice.equals("5")) {
            System.out.print("Enter user's login to make him/her a researcher: ");
            String targetLogin = scanner.nextLine();

            User targetUser = null;
            for (User u : storage.getUsers()) {
                if (u.getLogin().equals(targetLogin)) {
                    targetUser = u;
                    break;
                }
            }

            if (targetUser == null) {
                System.out.println("User not found.");
            }
            else if (targetUser.getResearcherData() != null) {
                System.out.println("User is already a researcher.");
            }
            else {
                ResearcherDecorator rd = new ResearcherDecorator(targetUser); //decorator
                targetUser.setResearcherData(rd);

                String msg = String.format("Admin %s assigned Researcher status to %s", currentUser.getLastName(), targetUser.getLogin());
                storage.addLog(msg);
                storage.save();
                System.out.println("Success! " + targetUser.getFirstName() + " is now a researcher.");
            }
        }
        else if (choice.equals("0")) {
            logout();
        }
    }

    public void showStudentMenu() {
        System.out.println("1. View Courses\n2. View Marks\n0. Logout");

        if (currentUser.getResearcherData() != null) {
            System.out.println("R. Researcher Panel");
        }
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            // courses need to add
        } else if (choice.equalsIgnoreCase("R") && currentUser.getResearcherData() != null) {
            showResearcherMenu();
        } else if (choice.equals("0")) {
            logout();
        }
    }


    public void showResearcherMenu() {
        ResearcherDecorator res = currentUser.getResearcherData();
        System.out.println("\n--- RESEARCHER MENU ---");
        System.out.println("1. View Papers");
        System.out.println("2. Add Paper");
        System.out.println("3. View H-Index");
        System.out.println("0. Back");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.println("\nSort by: 1. Citations | 2. Date | 3. Length");
                String sortChoice = scanner.nextLine();

                Comparator<ResearchPaper> selectedComparator;

                if (sortChoice.equals("1")) {
                    selectedComparator = new CitationsComparator();
                } else if (sortChoice.equals("2")) {
                    selectedComparator = new DateComparator();
                } else if (sortChoice.equals("3")) {
                    selectedComparator = new LengthComparator();
                } else {
                    System.out.println("Invalid choice, using default (Citations).");
                    selectedComparator = new CitationsComparator();
                }
                res.printPapers(selectedComparator);
                break;
            case "2":
                addNewPaper(res);
                break;
            case "3":
                System.out.println("Your H-Index is: " + res.calculateHIndex());
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private void addNewPaper(ResearcherDecorator res) {
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Journal: ");
        String journal = scanner.nextLine();
        System.out.print("Enter DOI: ");
        String doi = scanner.nextLine();
        System.out.print("Enter Pages: ");
        int pages = Integer.parseInt(scanner.nextLine());

        List<Researcher> authors = new ArrayList<>();
        authors.add(res);

        ResearchPaper paper = new ResearchPaper(title, authors, journal, pages, new Date(), doi);
        res.addPaper(paper);
        System.out.println("Paper added successfully!");
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

//public void makeUserResearcher() {
//    System.out.print("Enter user login to make researcher: ");
//    String login = scanner.nextLine();
//
//    // 1. Ищем пользователя в DataStorage
//    User user = storage.getUsers().stream()
//            .filter(u -> u.getLogin().equals(login))
//            .findFirst()
//            .orElse(null);
//
//    if (user == null) {
//        System.out.println("User not found.");
//        return;
//    }
//
//    // 2. Проверяем, не является ли он уже исследователем
//    if (user.getResearcherData() != null) {
//        System.out.println("This user is already a researcher.");
//        return;
//    }
//
//    // 3. Создаем декоратор и привязываем его к пользователю
//    ResearcherDecorator decorator = new ResearcherDecorator(user);
//    user.setResearcherData(decorator);
//
//    // 4. Сохраняем изменения в файл
//    storage.save();
//
//    System.out.println(user.getLastName() + " is now a Researcher!");
//}