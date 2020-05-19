package de.paul2708.commands.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation declares a method to a command.
 *
 * @author Paul2708
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * This permission indicates that the sender needs operation rights (OP).
     */
    String OP_PERMISSION = "*";

    /**
     * This permission indicates that none permission is needed.
     */
    String NONE_PERMISSION = "";

    /**
     * Array of parents.
     *
     * @return array of parents or an empty array if no parent is present
     */
    String[] parent() default {};

    /**
     * Command name.
     *
     * @return command name
     */
    String name();

    /**
     * Command description. Default value is an empty string.
     *
     * @return command description
     */
    String desc() default "";

    /**
     * Permission, a player needs, to execute the command.
     * '*' means that the player requires op rights.
     * An empty string means that no permission is needed at all.
     * Default value is ''.
     *
     * @return command permission
     */
    String permission() default Command.NONE_PERMISSION;

    /**
     * Command aliases. Default value is an empty array.
     *
     * @return command aliases
     */
    String[] aliases() default {};


}
