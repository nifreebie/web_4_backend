package web.web4_backend.exceptions;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
