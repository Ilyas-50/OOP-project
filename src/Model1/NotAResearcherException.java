package Model1;

public class NotAResearcherException extends UniversitySystemException {
    private static final long serialVersionUID = 1L;

    public NotAResearcherException() {
        super();
    }

    public NotAResearcherException(String message) {
        super(message);
    }
}
