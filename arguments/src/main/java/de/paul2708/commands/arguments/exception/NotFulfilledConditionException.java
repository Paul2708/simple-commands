package de.paul2708.commands.arguments.exception;

/**
 * This exception will be thrown if a condition is not fulfilled.
 *
 * @see de.paul2708.commands.arguments.CommandArgument#condition(boolean, String)
 * @author Paul2708
 */
public class NotFulfilledConditionException extends IllegalStateException {

    private String description;

    /**
     * Create a new not fulfilled condition exception with a description.
     *
     * @param description text that describes the condition
     */
    public NotFulfilledConditionException(String description) {
        this.description = description;
    }

    /**
     * Get the condition description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }
}
