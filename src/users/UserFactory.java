package users;

/**
 * Factory class for creating User instances by type.
 * Implements Factory design pattern.
 */
public class UserFactory {

    /**
     * Creates and returns a new User of the given type.
     * @param type the type of user to create
     * @return new User instance, or null if type is null or unknown
     */
    public static User createUser(UserType type) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case STUDENT:  return new Student();
            case TEACHER:  return new Teacher();
            case MANAGER:  return new Manager();
            case ADMIN:    return new Admin();
            default:       return null;
        }
    }
}