package customExceptions;

public class CategoryAlreadRegisteredException extends RuntimeException{

    public CategoryAlreadRegisteredException(String message) {
        super(message);
    }
}
