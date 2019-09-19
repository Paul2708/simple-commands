package arguments.primitive;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.primitive.DoubleArgument;
import de.paul2708.commands.arguments.util.Pair;

/**
 * This class tests the double command argument.
 *
 * @author Paul2708
 */
public final class DoubleArgumentTest extends AbstractArgumentTest {

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new DoubleArgument();
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("0", 0.0),
                Pair.of("1337", 1337.0),
                Pair.of("-5,5", -5.5),
                Pair.of("0.001", 0.001)
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
