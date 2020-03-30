package springbook.user.Exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(Throwable cause){
        super(cause);
    }
}
