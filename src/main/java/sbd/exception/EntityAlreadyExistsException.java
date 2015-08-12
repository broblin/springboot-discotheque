package sbd.exception;

/**
 * Created by benoit on 07/08/15.
 */
public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
