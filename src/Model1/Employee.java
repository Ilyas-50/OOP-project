package Model1;

import java.io.*;
import java.util.*;

public abstract class Employee extends User {

    private double salary;
    public Employee() {
        super();
    }
    public Employee(String id, String login, String password, String firstName, String lastName, String email, double salary) {
        super(id, login, password, firstName, lastName, email);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
}