package co.wordly.data.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Class clazz, String id) {
        super(String.format("Could not found %s with ID of %s",
                clazz.getSimpleName(), id));
    }

}
