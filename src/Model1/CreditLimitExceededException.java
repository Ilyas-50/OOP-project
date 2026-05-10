package Model1;

public class CreditLimitExceededException extends UniversitySystemException {
    public CreditLimitExceededException() {
        super();
    }

    public CreditLimitExceededException(String message) {
        super(message);
    }
}
