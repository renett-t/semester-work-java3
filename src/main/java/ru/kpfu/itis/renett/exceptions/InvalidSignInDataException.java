package ru.kpfu.itis.renett.exceptions;

public class InvalidSignInDataException extends RuntimeException {
    public InvalidSignInDataException() {
    }

    public InvalidSignInDataException(String s) {
        super(s);
    }

    public InvalidSignInDataException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidSignInDataException(Throwable throwable) {
        super(throwable);
    }

    public InvalidSignInDataException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
