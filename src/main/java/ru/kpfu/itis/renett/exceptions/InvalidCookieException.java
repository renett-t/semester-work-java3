package ru.kpfu.itis.renett.exceptions;

public class InvalidCookieException extends RuntimeException {
    public InvalidCookieException() {
    }

    public InvalidCookieException(String s) {
        super(s);
    }

    public InvalidCookieException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidCookieException(Throwable throwable) {
        super(throwable);
    }

    public InvalidCookieException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
