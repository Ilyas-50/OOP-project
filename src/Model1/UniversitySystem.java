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
        System.out.println("1. Add User\n2. Remove User\n3. View Logs\n4. View Users\n5. Make someone Researcher");
        System.out.println("6. Messages");
        System.out.println("7. Change Password");
        System.out.println("0. Logout");
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
            if (newUser instanceof Manager) {
                Manager m = (Manager) newUser;
                System.out.println("Select Manager Type: 1. Office of Registrar (OR) | 2. Department");
                String mType = scanner.nextLine();
                m.setManagerType(mType.equals("1") ? ManagerType.OR : ManagerType.DEPARTAMENT);
            }
            if (newUser instanceof Teacher) {
                Teacher t = (Teacher) newUser;
                System.out.println("Select Teacher Title: 1. Tutor | 2. Lector | 3. Senior Lector | 4. Professor");
                String tType = scanner.nextLine();
                switch (tType) {
                    case "1": t.setTitle(TeacherTitle.TUTOR); break;
                    case "2": t.setTitle(TeacherTitle.LECTOR); break;
                    case "3": t.setTitle(TeacherTitle.SENIOR_LECTOR); break;
                    case "4":
                        t.setTitle(TeacherTitle.PROFESSOR);
                        ResearcherDecorator rd = new ResearcherDecorator(t);
                        t.setResearcherData(rd);
                        System.out.println("Professor automatically assigned Researcher status.");
                        break;
                    default:
                        t.setTitle(TeacherTitle.LECTOR);
                        System.out.println("Invalid choice, defaulting to Lector.");
                }
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
                System.out.println("No users found.");
            } else {
                System.out.println("\n--- Users ---");
                for (User u : users) {
                    System.out.printf("Login: %-10s | Password: %-10s | Name: %-20s | Status: %s%n",
                            u.getLogin(), u.getPassword(),
                            u.getFirstName() + " " + u.getLastName(),
                            u.getClass().getSimpleName());
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
        else if (choice.equals("6")) { showMessagesMenu(currentUser); }
        else if (choice.equals("7")) { changePassword(currentUser); }
        else if (choice.equals("0")) {
            logout();
        }
    }

    public void showStudentMenu() {
        Student student = (Student) currentUser;

        System.out.println("1. View My Courses");
        System.out.println("2. View Transcript (Marks)");
        System.out.println("3. Register to a Course");
        System.out.println("4. Rate a Teacher");
        System.out.println("5. Change Password");
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

            case "4":
                rateTeacher(student);
                break;

            case "5": changePassword(currentUser); break;

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
        System.out.println("8. Cite a Paper(цитирование)");
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
            case "8":
                citePaper(res);
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

        if (manager.getManagerType() == ManagerType.OR) {
            showORManagerMenu(manager);
        } else {
            showDepartmentManagerMenu(manager);
        }
    }

    private void showORManagerMenu(Manager manager) {
        System.out.println("\n--- MANAGER MENU (Office of Registrar) ---");
        System.out.println("1. View All Students");
        System.out.println("2. View All Teachers");
        System.out.println("3. Statistical Report");
        System.out.println("4. View Teacher Ratings");
        System.out.println("5. View All Research Papers");
        System.out.println("6. Messages");
        System.out.println("7. Change Password");
        System.out.println("0. Logout");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.println("Sort by: 1. GPA | 2. Alphabetically");
            String sort = scanner.nextLine();

            List<Student> students = new ArrayList<>();
            for (User u : storage.getUsers()) {
                if (u instanceof Student) students.add((Student) u);
            }

            if (sort.equals("1")) {
                students.sort((a, b) -> Double.compare(b.getGpa(), a.getGpa()));
            } else {
                students.sort(Comparator.comparing(User::getLastName));
            }

            System.out.println("\n--- Students ---");
            for (Student s : students) {
                System.out.printf("%-20s | Faculty: %-6s | Year: %d | GPA: %.2f%n",
                        s.getFirstName() + " " + s.getLastName(),
                        s.getFaculty(), s.getYearOfStudy(), s.getGpa());
            }
        }
        else if (choice.equals("2")) {
            System.out.println("Sort by: 1. Rating | 2. Alphabetically");
            String sort = scanner.nextLine();

            List<Teacher> teachers = new ArrayList<>();
            for (User u : storage.getUsers()) {
                if (u instanceof Teacher) teachers.add((Teacher) u);
            }

            if (sort.equals("1")) {
                teachers.sort((a, b) -> Double.compare(b.getAverageRating(), a.getAverageRating()));
            } else {
                teachers.sort(Comparator.comparing(User::getLastName));
            }

            System.out.println("\n--- Teachers ---");
            for (Teacher t : teachers) {
                System.out.printf("%-20s | Title: %-15s | Rating: %.1f/10%n",
                        t.getFirstName() + " " + t.getLastName(),
                        t.getTitle(), t.getAverageRating());
            }
        }
        else if (choice.equals("3")) {
            manager.printStatisticalReport();
        }
        else if (choice.equals("4")) {
            System.out.println("\n--- Teacher Ratings ---");
            boolean found = false;
            for (User u : storage.getUsers()) {
                if (u instanceof Teacher) {
                    Teacher t = (Teacher) u;
                    found = true;
                    System.out.printf("%-20s | Rating: %.1f/10 (%d votes)%n",
                            t.getFirstName() + " " + t.getLastName(),
                            t.getAverageRating(), t.getRatings().size());
                }
            }
            if (!found) System.out.println("No teachers found.");
        }
        else if (choice.equals("5")) {
            List<ResearchPaper> allPapers = storage.getAllPapers();
            if (allPapers.isEmpty()) {
                System.out.println("No research papers in the system.");
                return;
            }

            System.out.println("Sort by: 1. Citations | 2. Date | 3. Length");
            String sort = scanner.nextLine();

            Comparator<ResearchPaper> comparator;
            if (sort.equals("2"))      comparator = new DateComparator();
            else if (sort.equals("3")) comparator = new LengthComparator();
            else                       comparator = new CitationsComparator();

            List<ResearchPaper> sorted = new ArrayList<>(allPapers);
            sorted.sort(comparator);

            System.out.println("\n--- All Research Papers ---");
            for (int i = 0; i < sorted.size(); i++) {
                System.out.println((i + 1) + ". " + sorted.get(i).getCitationInFormat()
                        + " | Citations: " + sorted.get(i).getCitations());
            }
        }
        else if (choice.equals("6")) { showMessagesMenu(currentUser); }
        else if (choice.equals("7")) { changePassword(currentUser); }
        else if (choice.equals("0")) {
            logout();
        }
    }

    private void showDepartmentManagerMenu(Manager manager) {
        System.out.println("\n--- MANAGER MENU (Department) ---");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Assign Course to Teacher");
        System.out.println("4. Update Student Year of Study");
        System.out.println("5. Assign Supervisor to Student");
        System.out.println("6. Messages");
        System.out.println("7. Change Password");
        System.out.println("0. Logout");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.print("Enter Course Code (e.g. CS101): ");
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
            List<Course> allCourses = storage.getCourses();
            if (allCourses.isEmpty()) {
                System.out.println("No courses registered yet.");
            } else {
                for (Course c : allCourses) {
                    System.out.println(c.getCourseName() + " (" + c.getCourseCode() + ") : " + c.getCredits() + " credits");
                }
            }
        }
        else if (choice.equals("3")) {
            assignCourseLogic(manager);
        }
        else if (choice.equals("4")) {
            System.out.println("\nStudents:");
            boolean found = false;
            for (User u : storage.getUsers()) {
                if (u instanceof Student) {
                    Student s = (Student) u;
                    System.out.printf("%-20s | Login: %-10s | Year: %d | Faculty: %s%n",
                            s.getFirstName() + " " + s.getLastName(),
                            s.getLogin(), s.getYearOfStudy(), s.getFaculty());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No students in the system.");
                return;
            }
            System.out.print("Enter student login: ");
            String login = scanner.nextLine();

            User target = storage.getUserByLogin(login);
            if (target == null || !(target instanceof Student)) {
                System.out.println("Student not found.");
                return;
            }

            Student student = (Student) target;
            System.out.println("Current year: " + student.getYearOfStudy());
            System.out.print("Enter new year (1-4): ");

            try {
                int year = Integer.parseInt(scanner.nextLine());
                if (year < 1 || year > 4) {
                    System.out.println("Year must be between 1 and 6.");
                    return;
                }
                student.setYearOfStudy(year);
                storage.addLog("Manager " + manager.getLastName() + " updated year of "
                        + student.getLastName() + " to " + year);
                if (year == 4) {
                    assignSupervisor(student, manager);
                }
                storage.save();
                System.out.println("Year updated successfully.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        else if (choice.equals("5")) {
            System.out.println("\nStudents:");
            boolean found = false;
            for (User u : storage.getUsers()) {
                if (u instanceof Student) {
                    Student s = (Student) u;
                    String supervisorInfo = s.getSupervisor() != null
                            ? ((ResearcherDecorator) s.getSupervisor()).getUser().getLastName()
                            : "none";
                    System.out.printf("%-20s | Login: %-10s | Year: %d | Supervisor: %s%n",
                            s.getFirstName() + " " + s.getLastName(),
                            s.getLogin(), s.getYearOfStudy(), supervisorInfo);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No students in the system.");
                return;
            }

            System.out.print("Enter student login: ");
            String studentLogin = scanner.nextLine();
            User targetStudent = storage.getUserByLogin(studentLogin);

            if (targetStudent == null || !(targetStudent instanceof Student)) {
                System.out.println("Student not found.");
                return;
            }

            assignSupervisor((Student) targetStudent, manager);
            storage.save();
        }
        else if (choice.equals("6")) { showMessagesMenu(currentUser); }
        else if (choice.equals("7")) { changePassword(currentUser); }
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
        System.out.println("4. Messages");
        System.out.println("5. Change Password");
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
            case "4":
                showMessagesMenu(currentUser); break;
            case "5": changePassword(currentUser); break;
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

    private void sendMessage(User sender) {
        System.out.println("\n--- Users ---");
        for (User u : storage.getUsers()) {
            if (!u.getLogin().equals(sender.getLogin())) {
                System.out.printf("Login: %-10s | %s %s | %s%n",
                        u.getLogin(), u.getFirstName(), u.getLastName(),
                        u.getClass().getSimpleName());
            }
        }

        System.out.print("Enter receiver login: ");
        String receiverLogin = scanner.nextLine();
        User receiver = storage.getUserByLogin(receiverLogin);

        if (receiver == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter message: ");
        String text = scanner.nextLine();
        storage.getMessageSystem().sendMessage(sender, receiver, text);
    }

    private void showMessagesMenu(User user) {
        System.out.println("\n--- MESSAGES ---");
        System.out.println("1. Send Message");
        System.out.println("2. View Messages");
        System.out.println("0. Back");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            sendMessage(user);
        } else if (choice.equals("2")) {
            List<Message> inbox = storage.getMessageSystem().getInbox(user);
            if (inbox.isEmpty()) {
                System.out.println("No messages");
                return;
            }
            System.out.println("\n--- Inbox ---");
            for (Message m : inbox) {
                System.out.println(m);
            }
            storage.save();
        }
    }


    private void changePassword(User user) {
        System.out.print("Enter current password: ");
        String current = scanner.nextLine();

        if (!user.getPassword().equals(current)) {
            System.out.println("Incorrect current password.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine();

        user.setPassword(newPass);
        storage.addLog("User " + user.getLogin() + " changed their password.");
        storage.save();
        System.out.println("Password changed successfully!");
    }

    private void assignSupervisor(Student student, Manager manager) {
        System.out.println("\nStudent is now 4th year — a supervisor must be assigned.");

        System.out.println("--- Available Researchers ---");
        boolean found = false;
        for (User u : storage.getUsers()) {
            if (u.getResearcherData() != null) {
                ResearcherDecorator rd = u.getResearcherData();
                System.out.printf("Login: %-10s | Name: %-20s | H-Index: %d%n",
                        u.getLogin(),
                        u.getFirstName() + " " + u.getLastName(),
                        rd.calculateHIndex());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No researchers available in the system.");
            return;
        }

        System.out.print("Enter researcher login to assign as supervisor: ");
        String login = scanner.nextLine();

        User target = storage.getUserByLogin(login);

        if (target == null) {
            System.out.println("User not found.");
            return;
        }

        if (target.getResearcherData() == null) {
            System.out.println("This user is not a researcher.");
            return;
        }

        ResearcherDecorator rd = target.getResearcherData();

        try {
            if (rd.calculateHIndex() < 3) {
                throw new LowHIndexException("Cannot assign supervisor: H-Index is "
                        + rd.calculateHIndex() + " (minimum required: 3).");
            }
            student.setSupervisor(rd);
            storage.addLog("Manager " + manager.getLastName() + " assigned "
                    + target.getLastName() + " as supervisor for " + student.getLastName());
            System.out.println("Supervisor assigned successfully!");

        } catch (LowHIndexException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void citePaper(ResearcherDecorator res) {
        List<ResearchPaper> allPapers = storage.getAllPapers();
        if (allPapers.isEmpty()) {
            System.out.println("No papers in the system to cite.");
            return;
        }

        System.out.println("\n--- All Papers in System ---");
        for (int i = 0; i < allPapers.size(); i++) {
            System.out.println((i + 1) + ". " + allPapers.get(i).getTitle()
                    + " | Citations: " + allPapers.get(i).getCitations());
        }

        System.out.print("Select paper to cite: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= allPapers.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            ResearchPaper selected = allPapers.get(idx);
            if (selected.getAuthors().contains(res)) {
                System.out.println("You cannot cite your own paper.");
                return;
            }
            selected.addCitation();
            storage.addLog("Researcher " + res.getUser().getLastName()
                    + " cited paper: " + selected.getTitle());
            storage.save();
            System.out.println("Cited! Total citations: " + selected.getCitations());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void rateTeacher(Student student) {
        List<Teacher> rateableTeachers = new ArrayList<>();

        for (User u : storage.getUsers()) {
            if (u instanceof Teacher) {
                Teacher t = (Teacher) u;
                for (Course c : t.getCourses()) {
                    if (student.getTranscript().isCoursePassed(c) && !rateableTeachers.contains(t)) {
                        rateableTeachers.add(t);
                    }
                }
            }
        }

        if (rateableTeachers.isEmpty()) {
            System.out.println("You have no completed courses to rate teachers.");
            return;
        }

        System.out.println("\n--- Rate a Teacher ---");
        for (int i = 0; i < rateableTeachers.size(); i++) {
            Teacher t = rateableTeachers.get(i);
            System.out.println((i + 1) + ". " + t.getFirstName() + " " + t.getLastName()
                    + " | Current rating: " + String.format("%.1f", t.getAverageRating()));
        }

        System.out.print("Select teacher: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= rateableTeachers.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            Teacher selected = rateableTeachers.get(idx);

            System.out.print("Enter rating (0-10): ");
            int rating = Integer.parseInt(scanner.nextLine());

            selected.addRating(rating);
            storage.addLog("Student " + student.getLastName() + " rated teacher "
                    + selected.getLastName() + ": " + rating);
            storage.save();
            System.out.println("Rating submitted!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
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
            System.out.println("You have no courses to manage.");
            return;
        }

        System.out.println("\nSelect Course:");
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + ". " + myCourses.get(i).getCourseName());
        }

        int courseIdx;
        try {
            System.out.print("Enter number: ");
            courseIdx = Integer.parseInt(scanner.nextLine()) - 1;
            if (courseIdx < 0 || courseIdx >= myCourses.size()) {
                System.out.println("Invalid selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
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
            System.out.println("No students found for this course.");
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
            System.out.println("Student '" + login + "' not found on this course.");
            return;
        }

        try {
            System.out.print("Enter 1st Attestation score (0-30): ");
            double att1 = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter 2nd Attestation score (0-30): ");
            double att2 = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Final Exam score (0-40): ");
            double finalExam = Double.parseDouble(scanner.nextLine());

            if (att1 < 0 || att2 < 0 || att1 + att2 > 60 || finalExam < 0 || finalExam > 40) {
                System.out.println("Invalid scores. ATT1 + ATT2 must be <= 60, Final: 0-40.");
                return;
            }

            teacher.putMark(selectedStudent, selectedCourse, att1, att2, finalExam);
            storage.save();

            double total = att1 + att2 + finalExam;
            System.out.println("Mark added successfully! Total: " + total + " (" + new Mark(selectedCourse, att1, att2, finalExam).getLetterDigit() + ")");

        } catch (NumberFormatException e) {
            System.out.println("Invalid format. Use numbers (e.g. 25.5).");
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