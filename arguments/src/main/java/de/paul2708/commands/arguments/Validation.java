package de.paul2708.commands.arguments;

import de.paul2708.commands.language.MessageResource;

/**
 * This class models a parsing result, used to check passed command arguments.
 *
 * @param <T> parsed object type
 * @author Paul2708
 */
public final class Validation<T> {

    private final boolean valid;

    private final T parsedObject;
    private final MessageResource errorMessageResource;

    /**
     * Create a new validation.
     *
     * @param valid true if it's valid, otherwise false
     * @param parsedObject parsed object
     * @param errorMessageResource error message resource, if the argument is not valid
     */
    private Validation(boolean valid, T parsedObject, MessageResource errorMessageResource) {
        this.valid = valid;
        this.parsedObject = parsedObject;
        this.errorMessageResource = errorMessageResource;
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
     * Get the error message resource, the reason why the parsing failed.
     * Can be <code>null</code> if the validation is valid.
     *
     * @return error message resource (or <code>null</code>)
     */
    public MessageResource getErrorResource() {
        return errorMessageResource;
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
     * @param errorMessageResource error message resource
     * @param <T> ignored
     * @return invalid validation of type {@link Void}.
     */
    public static <T> Validation<T> invalid(MessageResource errorMessageResource) {
        return new Validation<>(false, null, errorMessageResource);
    }
}
