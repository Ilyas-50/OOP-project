package Model1;

public abstract class User {

    private String id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public User() {
    }

    public User(String id, String login, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public boolean login() {
        //DataStorage realization
        return false;
    }

    public void logout() {
        // Здесь будет логика выхода
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", name=" + firstName + " " + lastName + "]";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}