package de.paul2708.commands.language;

/**
 * This data class holds information about a message resource.
 * A message resource contains a key to the resource bundle message
 * and arguments that will be replaced for each placeholder.
 *
 * @author Paul2708
 */
public final class MessageResource {

    private final String key;
    private final Object[] arguments;

    /**
     * Get a message resource by its unique key and its arguments.
     * If <code>null</code> will be passed as arguments, an empty array will be used instead.
     *
     * @param key       resource bundle message key
     * @param arguments arguments that will be replaced for each placeholder
     */
    private MessageResource(String key, Object[] arguments) {
        this.key = key;
        this.arguments = arguments == null ? new Object[0] : arguments;
    }

    /**
     * Get the resource bundle message key.
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the arguments, that will replaced.
     *
     * @return array of objects
     */
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * Get a message resource by its unique key and its arguments.
     * If <code>null</code> will be passed as arguments, an empty array will be used instead.
     *
     * @param key       resource bundle message key
     * @param arguments arguments that will be replaced for each placeholder
     * @return message resource
     */
    public static MessageResource of(String key, Object... arguments) {
        return new MessageResource(key, arguments);
    }
}