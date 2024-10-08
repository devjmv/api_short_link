package dev.shortlink.user.user_exceptions;

public class UserNotFoundException extends UserException {

    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

