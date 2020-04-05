package arguments;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.arguments.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    @BeforeEach
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

            assertTrue(validation.isValid(), String.format("%s is not valid", pair.getKey()));
            assertEquals(pair.getValue(), validation.getParsedObject(),
                    String.format("expected: %s != actual: %s",
                                pair.getValue().toString(), validation.getParsedObject()));
        }
    }

    /**
     * Test an array of invalid arguments.
     */
    @Test
    public void testInvalidArguments() {
        for (String argument : invalidArguments()) {
            Validation<?> validation = commandArgument.validate(argument);

            assertFalse(validation.isValid(), "argument '" + argument + "' is valid");
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
