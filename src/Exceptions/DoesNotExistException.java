package Exceptions;

/**
 * Throws this error whenever there is an issue with an object that does not exist in our system.
 *
 * @version 1.1
 */

public class DoesNotExistException extends Exception {
    public DoesNotExistException(String message) {
        super(message);
    }
}
