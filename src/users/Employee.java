package users;

/**
 * Abstract base class for all university employees.
 * Extends User with salary information.
 */
public abstract class Employee extends User {

    private double salary;

    public Employee() {
        super();
    }

    /**
     * Creates an employee with full details including salary.
     */
    public Employee(String id, String login, String password, String firstName, String lastName, String email, double salary) {
        super(id, login, password, firstName, lastName, email);
        this.salary = salary;
    }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}