package de.paul2708.commands.arguments;

/**
 * This class models a parsing result, used to check passed command arguments.
 *
 * @param <T> parsed object type
 * @author Paul2708
 */
public final class Validation<T> {

    private final boolean valid;

    private final T parsedObject;
    private final String errorMessage;

    /**
     * Create a new validation.
     *
     * @param valid true if it's valid, otherwise false
     * @param parsedObject parsed object
     * @param errorMessage error message, if the
     */
    private Validation(boolean valid, T parsedObject, String errorMessage) {
        this.valid = valid;
        this.parsedObject = parsedObject;
        this.errorMessage = errorMessage;
    }

    /**
     * Check if the parsed object is valid.
     *
     * @return true if the validation is valid, otherwise false
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Get the parsed object. Can be <code>null</code> if the validation is invalid.
     *
     * @return parsed object (or <code>null</code>)
     */
    public T getParsedObject() {
        return parsedObject;
    }

    /**
     * Get the error message, the reason why the parsing failed.
     * Can be <code>null</code> if the validation is valid.
     *
     * @return error message (or <code>null</code>)
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Create a valid validation.
     *
     * @param parsedObject parsed object
     * @param <T> parsed object type
     * @return valid validation
     */
    public static <T> Validation<T> valid(T parsedObject) {
        return new Validation<>(true, parsedObject, null);
    }

    /**
     * Create an invalid validation.
     *
     * @param errorMessage error message
     * @return invalid validation of type {@link Void}.
     */
    public static Validation<Void> invalid(String errorMessage) {
        return new Validation<>(false, null, errorMessage);
    }
}
