package Model1;

public class UserFactory {

    public static User createUser(UserType type) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case STUDENT:
                return new Student();
            case TEACHER:
                return new Teacher();
            case MANAGER:
                return new Manager();
            case ADMIN:
                return new Admin();
            default:
                return null;
        }
    }
}