package arguments;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.primitive.IntegerArgument;
import de.paul2708.commands.arguments.util.Pair;

/**
 * This class tests the integer command argument.
 *
 * @author Paul2708
 */
public class IntegerArgumentTest extends AbstractArgumentTest {

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new IntegerArgument();
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("0", 0),
                Pair.of("+0", 0),
                Pair.of("-0", 0),
                Pair.of("0", 0),

                Pair.of("1337", 1337),
                Pair.of("-1337", -1337),
                Pair.of("+999999999", 999999999),
                Pair.of("0005", 5)
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
                "zero",
                "text",
                "",
                " ",
                "5test",
                "test5",
                "0-0",
                "0.25",
                "1,3",
                " 5"
        };
    }
}
