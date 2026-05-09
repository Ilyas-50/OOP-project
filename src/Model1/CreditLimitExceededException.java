package Model1;

public class CreditLimitExceededException extends UniversitySystemException {
    private static final long serialVersionUID = 1L;

    public CreditLimitExceededException() {
        super();
    }

    public CreditLimitExceededException(String message) {
        super(message);
    }
}
