package arguments.primitive;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.primitive.ByteArgument;
import de.paul2708.commands.arguments.impl.primitive.FloatArgument;
import de.paul2708.commands.arguments.util.Pair;

/**
 * This class tests the float command argument.
 *
 * @author Paul2708
 */
public class FloatArgumentTest extends AbstractArgumentTest {

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new FloatArgument();
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("0", 0.0f),
                Pair.of("1337", 1337.0f),
                Pair.of("-5,5", -5.5f),
                Pair.of("0.001", 0.001f)
        };
    }

    /**
     * Get an array of string arguments.
     *
     * @return array of strings
     */
    @Override
    public String[] invalidArguments() {
        return new String[] {
                "test",
                "0;13",
                "12,34,56"
        };
    }
}
