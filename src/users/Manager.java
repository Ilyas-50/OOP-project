package users;

import academic.Course;
import academic.Faculty;
import academic.Lesson;
import academic.ManagerType;
import core.DataStorage;
import java.util.*;

/**
 * Manager employee with administrative academic duties.
 * Two types: OR (Office of Registrar) and DEPARTMENT.
 */
public class Manager extends Employee {

    private ManagerType managerType;

    public Manager() {
        super();
    }

    /**
     * Prints university-wide academic statistics grouped by faculty.
     */
    public void printStatisticalReport() {
        DataStorage storage = DataStorage.getInstance();
        System.out.println("\n--- Statistical Report ---");
        int total = 0;
        double gpaSum = 0;
        Map<Faculty, List<Double>> byFaculty = new HashMap<>();

        for (User u : storage.getUsers()) {
            if (u instanceof Student) {
                Student s = (Student) u;
                total++;
                gpaSum += s.getGpa();
                byFaculty.computeIfAbsent(s.getFaculty(), k -> new ArrayList<>()).add(s.getGpa());
            }
        }

        System.out.println("Total students: " + total);
        System.out.printf("Average GPA: %.2f%n", total > 0 ? gpaSum / total : 0);
        System.out.println("\nBy Faculty:");
        for (Map.Entry<Faculty, List<Double>> entry : byFaculty.entrySet()) {
            double avg = entry.getValue().stream().mapToDouble(d -> d).average().orElse(0);
            System.out.printf("  %s: %d students, avg GPA %.2f%n", entry.getKey(), entry.getValue().size(), avg);
        }
    }

    /**
     * Adds a new course to the system.
     */
    public void addCourse(Course course) {
        DataStorage storage = DataStorage.getInstance();
        storage.getCourses().add(course);
        storage.addLog("Manager " + this.getLastName() + " added course: " + course.getCourseName());
        storage.save();
        System.out.println("Course " + course.getCourseName() + " added successfully.");
    }

    /**
     * Assigns a course to a teacher if not already assigned.
     */
    public void assignCourseToTeacher(Teacher teacher, Course course) {
        if (!teacher.getCourses().contains(course)) {
            teacher.addCourse(course);
            DataStorage.getInstance().addLog("Manager " + this.getLastName()
                    + " assigned " + course.getCourseName() + " to " + teacher.getLastName());
            DataStorage.getInstance().save();
        }
    }

    /**
     * Adds a lesson to a teacher's schedule.
     */
    public void addLessonToTeacher(Teacher teacher, Lesson lesson) {
        teacher.addLesson(lesson);
        DataStorage.getInstance().addLog("Manager " + this.getLastName()
                + " added lesson " + lesson + " to teacher " + teacher.getLastName());
        DataStorage.getInstance().save();
    }

    public ManagerType getManagerType() { return managerType; }
    public void setManagerType(ManagerType managerType) { this.managerType = managerType; }
}