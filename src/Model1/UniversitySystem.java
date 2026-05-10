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
        String postfix = (currentUser instanceof Student) ? " (" + ((Student)currentUser).getFaculty() + ", Year " + ((Student)currentUser).getYearOfStudy() + ")" : "";
        System.out.println("Your status: " + researcherPrefix + status + postfix);

        if (currentUser instanceof Admin) {
            showAdminMenu();
        } else if (currentUser instanceof Student) {
            showStudentMenu();
        }
        else if (currentUser instanceof Manager) {
            showManagerMenu();
        }
        else if (currentUser instanceof Teacher) {
            showTeacherMenu();
        }

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
            else if (!(targetUser instanceof Student || targetUser instanceof Teacher)) {
                System.out.println("Error: Only Students and Teachers can become researchers.");
            }
            else if (targetUser.getResearcherData() != null) {
                System.out.println("User is already a researcher.");
            }
            else {
                ResearcherDecorator rd = new ResearcherDecorator(targetUser);
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
        Student student = (Student) currentUser;

        System.out.println("1. View My Courses");
        System.out.println("2. View Transcript (Marks)");
        System.out.println("3. Register to a Course");
        System.out.println("0. Logout");

        if (student.getResearcherData() != null) {
            System.out.println("R. Researcher Panel");
        }

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                student.viewCourses();
                break;

            case "2":
                student.viewTranscript();
                break;

            case "3":
                registerStudentToCourse(student);
                break;

            case "R":
            case "r":
                if (student.getResearcherData() != null) {
                    showResearcherMenu();
                }
                break;

            case "0":
                logout();
                return;

            default:
                System.out.println("Invalid option.");
        }
    }

    public void showResearcherMenu() {
        ResearcherDecorator res = currentUser.getResearcherData();
        System.out.println("\n--- RESEARCHER MENU ---");
        System.out.println("1. View My Papers");
        System.out.println("2. Add Paper");
        System.out.println("3. View H-Index");
        System.out.println("4. View My Projects");
        System.out.println("5. Create Research Project");
        System.out.println("6. Join Research Project");
        System.out.println("7. Add Paper to Project");
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

            case "4":
                List<ResearchProject> myProjects = res.getMyProjects();
                if (myProjects.isEmpty()) {
                    System.out.println("You are not a member of any research project.");
                } else {
                    System.out.println("--- Your Research Projects ---");
                    for (int i = 0; i < myProjects.size(); i++) {
                        System.out.println((i + 1) + ". " + myProjects.get(i));
                    }
                }
                break;


            case "5":
                System.out.print("Enter project topic: ");
                res.createProject(scanner.nextLine());
                System.out.println("Project created successfully!");
                break;

            case "6":
                joinResearchProject(res);
                break;
            case "7":
                addPaperToProject(res);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    public void showManagerMenu() {
        Manager manager = (Manager) currentUser;
        System.out.println("\n--- MANAGER MENU ---");
        System.out.println("1. Add Course to System");
        System.out.println("2. View All Courses");
        System.out.println("3. Assign Course to Teacher");
        System.out.println("0. Logout");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.print("Enter Course Code (CS101): ");
            String code = scanner.nextLine();
            System.out.print("Enter Course Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Credits: ");
            int credits = Integer.parseInt(scanner.nextLine());

            Course newCourse = new Course(code, name, credits);

            System.out.print("Does it have prerequisites? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                addPrerequisiteToCourse(newCourse);
            }

            manager.addCourse(newCourse);
        }
        else if (choice.equals("2")) {
            System.out.println("Courses:");
            List<Course> allCourses = storage.getCourses();

            if (allCourses.isEmpty()) {
                System.out.println("No courses registered yet.");
            } else {
                for (Course c : allCourses) {
                    System.out.println(c.getCourseName() + "(" + c.getCourseCode() + ") : " + c.getCredits() + " credits");
                }
            }
        }
        else if (choice.equals("3")) {
            assignCourseLogic(manager);
        }
        else if (choice.equals("0")) {
            logout();
        }
    }

    public void showTeacherMenu() {
        Teacher teacher = (Teacher) currentUser;
        System.out.println("\n--- TEACHER MENU ---");
        System.out.println("1. View My Courses");
        System.out.println("2. Put Marks");
        System.out.println("3. View Students Info");
        System.out.println("0. Logout");

        if (teacher.getResearcherData() != null) {
            System.out.println("R. Researcher Panel");
        }

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                viewTeacherCourses(teacher);
                break;
            case "2":
                manageMarks(teacher);
                break;
            case "3":
                viewCourseStudents(teacher);
                break;
            case "R":
            case "r":
                if (teacher.getResearcherData() != null) showResearcherMenu();
                break;
            case "0":
                logout();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void viewCourseStudents(Teacher teacher) {
        List<Course> myCourses = teacher.getCourses();
        if (myCourses == null || myCourses.isEmpty()) {
            System.out.println("You don't have any assigned courses.");
            return;
        }

        System.out.println("\n--- Select Course to view Active Students ---");
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + ". " + myCourses.get(i).getCourseName());
        }

        System.out.print("Enter number: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= myCourses.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            Course selectedCourse = myCourses.get(idx);

            System.out.println("\n--- Active Students on " + selectedCourse.getCourseName() + " ---");
            boolean found = false;

            for (User u : storage.getUsers()) {
                if (u instanceof Student) {
                    Student s = (Student) u;
                    boolean isEnrolled = s.getEnrolledCourses().contains(selectedCourse);
                    boolean isFinished = s.getTranscript().isCoursePassed(selectedCourse);

                    if (isEnrolled && !isFinished) {
                        System.out.println("- " + s.getFirstName() + " " + s.getLastName()
                                + " | GPA: " + s.getGpa()
                                + " | Year: " + s.getYearOfStudy());
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("No active students found for this course.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void assignCourseLogic(Manager manager) {
        List<Course> allCourses = storage.getCourses();
        if (allCourses.isEmpty()) {
            System.out.println("No courses in system. Create a course first.");
            return;
        }

        for (int i = 0; i < allCourses.size(); i++) {
            System.out.println((i + 1) + ". " + allCourses.get(i));
        }
        System.out.print("Select course number: ");
        int cIdx = Integer.parseInt(scanner.nextLine()) - 1;
        Course selectedCourse = allCourses.get(cIdx);

        System.out.println("\n--- Available Teachers ---");
        for (User u : storage.getUsers()) {
            if (u instanceof Teacher) {
                System.out.println("- " + u.getLastName() + " (Login: " + u.getLogin() + ")");
            }
        }

        System.out.print("Enter teacher's login: ");
        String login = scanner.nextLine();

        Teacher targetTeacher = null;
        for (User u : storage.getUsers()) {
            if (u instanceof Teacher && u.getLogin().equals(login)) {
                targetTeacher = (Teacher) u;
                break;
            }
        }

        if (targetTeacher != null) {
            manager.assignCourseToTeacher(targetTeacher, selectedCourse);
            System.out.println("Course successfully assigned to " + targetTeacher.getLastName());
        } else {
            System.out.println("Teacher not found.");
        }
    }

    private void viewTeacherCourses(Teacher teacher) {
        System.out.println("\nYour Courses: ");
        if (teacher.getCourses().isEmpty()) {
            System.out.println("You are not assigned to any courses.");
        } else {
            teacher.getCourses().forEach(c -> System.out.println("- " + c.getCourseName()));
        }
    }

    private void manageMarks(Teacher teacher) {
        List<Course> myCourses = teacher.getCourses();
        if (myCourses == null || myCourses.isEmpty()) {
            System.out.println("You have no courses to manage");
            return;
        }

        System.out.println("\nSelect Course: ");
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + ". " + myCourses.get(i).getCourseName());
        }

        int courseIdx;
        try {
            System.out.print("Enter number: ");
            courseIdx = Integer.parseInt(scanner.nextLine()) - 1;
            if (courseIdx < 0 || courseIdx >= myCourses.size()) {
                System.out.println("Invalid selection");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number");
            return;
        }
        Course selectedCourse = myCourses.get(courseIdx);

        System.out.println("\nStudents enrolled in " + selectedCourse.getCourseName() + ":");
        boolean hasStudents = false;
        for (User u : storage.getUsers()) {
            if (u instanceof Student) {
                Student s = (Student) u;
                if (s.getEnrolledCourses().contains(selectedCourse)) {
                    System.out.println("- " + s.getLastName() + " (Login: " + s.getLogin() + ")");
                    hasStudents = true;
                }
            }
        }

        if (!hasStudents) {
            System.out.println("No students found for this course");
            return;
        }

        System.out.print("\nEnter student login to put mark: ");
        String login = scanner.nextLine();

        Student selectedStudent = null;
        for (User u : storage.getUsers()) {
            if (u instanceof Student && u.getLogin().equals(login)) {
                Student s = (Student) u;
                if (s.getEnrolledCourses().contains(selectedCourse)) {
                    selectedStudent = s;
                    break;
                }
            }
        }

        if (selectedStudent == null) {
            System.out.println("Student with login '" + login + "' not found on this course");
            return;
        }

        try {
            System.out.print("Enter total score for " + selectedStudent.getLastName() + " (0-100): ");
            double score = Double.parseDouble(scanner.nextLine());

            if (score < 0 || score > 100) {
                System.out.println("Score must be between 0 and 100");
                return;
            }

            teacher.putMark(selectedStudent, selectedCourse, score);
            storage.save();

            System.out.println("Mark (" + score + ") successfully added to " + selectedStudent.getLastName() + "'s transcript.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid score format. Use numbers (e.g. 85.5)");
        }
    }

    private void addPrerequisiteToCourse(Course newCourse) {
        List<Course> available = storage.getCourses();

        if (available.isEmpty()) {
            System.out.println("No existing courses to set as a prerequisite.");
            return;
        }

        System.out.println("\n--- Select one Prerequisite ---");
        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i));
        }
        System.out.println("0. No prerequisite");

        System.out.print("Enter choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice > 0 && choice <= available.size()) {
                Course selected = available.get(choice - 1);

                if (selected.getCourseName().equals(newCourse.getCourseName())) {
                    System.out.println("A course cannot be its own prerequisite.");
                } else {
                    newCourse.addPrerequisite(selected);
                    System.out.println("Prerequisite set: " + selected.getCourseName());
                }
            } else if (choice == 0) {
                System.out.println("No prerequisite assigned.");
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
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

//    private void createResearchProject(ResearcherDecorator res) {
//        System.out.print("Enter project topic: ");
//        String topic = scanner.nextLine();
//
//        res.createProject(topic);
//
//        System.out.println("Project created successfully!");
//    }

    private void joinResearchProject(ResearcherDecorator res) {
        List<ResearchProject> allProjects = storage.getAllProjects();
        if (allProjects.isEmpty()) {
            System.out.println("No research projects available.");
            return;
        }
        System.out.println("--- Available Projects ---");
        for (int i = 0; i < allProjects.size(); i++) {
            System.out.println((i + 1) + ". " + allProjects.get(i).getTopic());
        }
        System.out.print("Enter project number: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index < 0 || index >= allProjects.size()) {
                System.out.println("Invalid project number.");
                return;
            }
            allProjects.get(index).addParticipant(res);
            storage.addLog("Researcher " + res.getUser().getLastName() + " joined: " + allProjects.get(index).getTopic());
            storage.save();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        } catch (NotAResearcherException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addPaperToProject(ResearcherDecorator res) {
        List<ResearchProject> myProjects = res.getMyProjects();
        if (myProjects.isEmpty()) {
            System.out.println("You are not a member of any project.");
            return;
        }
        System.out.println("--- Your Projects ---");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.println((i + 1) + ". " + myProjects.get(i).getTopic());
        }
        System.out.print("Select project: ");
        try {
            int projIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (projIndex < 0 || projIndex >= myProjects.size()) {
                System.out.println("Invalid number.");
                return;
            }
            List<ResearchPaper> papers = res.getPapers();
            if (papers.isEmpty()) {
                System.out.println("You have no papers to add.");
                return;
            }
            System.out.println("--- Your Papers ---");
            for (int i = 0; i < papers.size(); i++) {
                System.out.println((i + 1) + ". " + papers.get(i).getTitle());
            }
            System.out.print("Select paper: ");
            int paperIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (paperIndex < 0 || paperIndex >= papers.size()) {
                System.out.println("Invalid number.");
                return;
            }
            myProjects.get(projIndex).addPaper(papers.get(paperIndex));
            storage.save();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void registerStudentToCourse(Student student) {
        List<Course> allCourses = storage.getCourses();
        if (allCourses.isEmpty()) {
            System.out.println("No courses available in the system.");
            return;
        }

        System.out.println("\n--- Available Courses ---");
        System.out.println("Your current credits: " + student.getTotalCredits() + " / 21");

        for (int i = 0; i < allCourses.size(); i++) {
            Course c = allCourses.get(i);
            System.out.println((i + 1) + ". " + c.getCourseName() + " (" + c.getCredits() + " credits)");
        }

        System.out.print("Select course number to register: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= allCourses.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            Course selected = allCourses.get(idx);

            if (student.getEnrolledCourses().contains(selected)) {
                System.out.println("You are already registered for this course.");
                return;
            }

            List<Teacher> availableTeachers = new ArrayList<>();
            for (User u : storage.getUsers()) {
                if (u instanceof Teacher) {
                    Teacher t = (Teacher) u;
                    if (t.getCourses().contains(selected)) {
                        availableTeachers.add(t);
                    }
                }
            }

            if (availableTeachers.isEmpty()) {
                System.out.println("Sorry, no teachers are currently assigned to this course.");
                return;
            }

            System.out.println("\nAvailable teachers for " + selected.getCourseName() + ":");
            for (int i = 0; i < availableTeachers.size(); i++) {
                System.out.println((i + 1) + ". " + availableTeachers.get(i).getLastName());
            }

            System.out.print("Select your teacher: ");
            int tIdx = Integer.parseInt(scanner.nextLine()) - 1;

            if (tIdx >= 0 && tIdx < availableTeachers.size()) {
                Teacher chosenTeacher = availableTeachers.get(tIdx);

                student.registerToCourse(selected);

                System.out.println("Registered to " + selected.getCourseName() + " with instructor " + chosenTeacher.getLastName());

                storage.save();
            } else {
                System.out.println("Invalid teacher selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (CreditLimitExceededException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
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