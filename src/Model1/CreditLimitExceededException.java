package Model1;

import java.io.*;
import java.util.*;

public class CreditLimitExceededException extends UniversitySystemException {

    public CreditLimitExceededException() {
        super();
    }

    public CreditLimitExceededException(String message) {
        super(message);
    }

}