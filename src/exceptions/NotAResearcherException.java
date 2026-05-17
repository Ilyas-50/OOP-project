package exceptions;

public class NotAResearcherException extends UniversitySystemException {
    public NotAResearcherException() {
        super();
    }

    public NotAResearcherException(String message) {
        super(message);
    }
}
