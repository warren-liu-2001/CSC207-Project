package Exceptions;

/**
 * Throws this error whenever there is an issue with an object that is invalid
 *
 * @version 1.1
 */

public class ObjectInvalidException extends Exception {
    public ObjectInvalidException(String message) {
        super(message);
    }
}
