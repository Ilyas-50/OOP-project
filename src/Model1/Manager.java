package Model1;

import java.io.*;
import java.util.*;

public class Manager extends Employee {

    private ManagerType managerType;

    public Manager() {
        super();
    }

    public void approveRegistration() {
        // TODO implement here
    }

    public void addCourse(Course course) {
        // TODO implement here
    }

    public void viewRequests() {
        // TODO implement here
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }
}