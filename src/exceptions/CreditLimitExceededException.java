package exceptions;

public class CreditLimitExceededException extends UniversitySystemException {
    public CreditLimitExceededException() {
        super();
    }

    public CreditLimitExceededException(String message) {
        super(message);
    }
}
