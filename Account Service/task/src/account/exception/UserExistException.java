package account.exception;

public class UserExistException extends RuntimeException{
    public UserExistException() {
        super();
    }

    public UserExistException(String message) {
        super(message);
    }
}
