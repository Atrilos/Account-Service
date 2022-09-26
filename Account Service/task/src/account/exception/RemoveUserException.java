package account.exception;

public class RemoveUserException extends RuntimeException{
    public RemoveUserException() {
        super();
    }

    public RemoveUserException(String message) {
        super(message);
    }
}
