package customExceptions;

public class CategoryNotRegisteredException extends RuntimeException{

    public CategoryNotRegisteredException(String message) {
        super(message);
    }
}
