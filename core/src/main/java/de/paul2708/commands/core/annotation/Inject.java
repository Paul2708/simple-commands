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
@Target(ElementType.FIELD)
public @interface Inject {

    /**
     * Specified key, if multiple objects will be injected.
     *
     * @return key
     */
    String key() default "";
}
