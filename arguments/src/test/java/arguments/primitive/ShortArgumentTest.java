package arguments.primitive;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.primitive.ShortArgument;
import de.paul2708.commands.arguments.util.Pair;

/**
 * This class tests the short command argument.
 *
 * @author Paul2708
 */
public final class ShortArgumentTest extends AbstractArgumentTest {

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new ShortArgument();
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("0", (short) 0),
                Pair.of("32767", (short) 32767),
                Pair.of("13", (short) 13),
                Pair.of("+100", (short) 100),
                Pair.of("-1", (short) -1)
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
                "32768",
                "0,2",
                "test"
        };
    }
}
