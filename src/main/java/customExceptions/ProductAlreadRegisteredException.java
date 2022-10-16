package customExceptions;

public class ProductAlreadRegisteredException extends RuntimeException{

    public ProductAlreadRegisteredException(String message) {
        super(message);
    }
}
