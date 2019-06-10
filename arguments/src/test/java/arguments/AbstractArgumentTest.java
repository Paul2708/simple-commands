package arguments;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.arguments.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This abstract test class provides two methods, that tests valid and invalid arguments.
 *
 * @author Paul2708
 */
public abstract class AbstractArgumentTest {

    private CommandArgument<?> commandArgument;

    /**
     * Create a new command argument.
     */
    @Before
    public void setUp() {
        this.commandArgument = create();
    }

    /**
     * Test an array of valid arguments.
     */
    @Test
    public void testValidArguments() {
        for (Pair<String, ?> pair : validArguments()) {
            Validation<?> validation = commandArgument.validate(pair.getKey());

            assertTrue(String.format("%s is not valid", pair.getKey()), validation.isValid());
            assertEquals(String.format("expected: %s != actual: %s",
                    pair.getValue().toString(), validation.getParsedObject()), pair.getValue(),
                    validation.getParsedObject());
        }
    }

    /**
     * Test an array of invalid arguments.
     */
    @Test
    public void testInvalidArguments() {
        for (String argument : invalidArguments()) {
            Validation<?> validation = commandArgument.validate(argument);

            assertFalse(validation.isValid());
        }
    }

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    public abstract CommandArgument<?> create();

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    public abstract Pair[] validArguments();

    /**
     * Get an array of string arguments.
     *
     * @return array of strings
     */
    public abstract String[] invalidArguments();
}
