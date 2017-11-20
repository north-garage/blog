package io.north.garage.blog.exception;

public class ApplicationSystemException extends RuntimeException {

    private static final long serialVersionUID = 548221462350964418L;

    public ApplicationSystemException(String message) {
        super(message);
    }

    public ApplicationSystemException(String message,
                                      Throwable cause) {
        super(message, cause);
    }
}
