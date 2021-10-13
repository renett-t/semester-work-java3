package ru.kpfu.itis.renett.exceptions;

public class InvalidRegistrationDataException extends RuntimeException {
    public InvalidRegistrationDataException() {
    }

    public InvalidRegistrationDataException(String message) {
        super(message);
    }

    public InvalidRegistrationDataException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidRegistrationDataException(Throwable throwable) {
        super(throwable);
    }

    public InvalidRegistrationDataException(String message, Throwable throwable, boolean b, boolean b1) {
        super(message, throwable, b, b1);
    }
}
