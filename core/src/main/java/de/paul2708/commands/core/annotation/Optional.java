package de.paul2708.commands.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks an argument as optional. This causes it to be skipped in case of failure while converting the
 * argument. This cannot be applied to primitives such as int, or bool, but to their respective boxed classes like
 * {@link Integer} and {@link Boolean}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Optional {
}
