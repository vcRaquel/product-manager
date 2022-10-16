package customExceptions;

public class ProductNotRegisteredException extends RuntimeException{

    public ProductNotRegisteredException(String message) {
        super(message);
    }
}
