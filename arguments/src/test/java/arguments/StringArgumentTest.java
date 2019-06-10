package arguments;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.StringArgument;
import de.paul2708.commands.arguments.util.Pair;

/**
 * This class tests the string command argument.
 *
 * @author Paul2708
 */
public class StringArgumentTest extends AbstractArgumentTest {

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new StringArgument();
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("hello", "hello"),
                Pair.of("Test123", "Test123"),
                Pair.of("sample,string", "sample,string"),
                Pair.of(" front_space", "front_space"),
                Pair.of("  test  ", "test"),
                Pair.of("13 ", "13"),
                Pair.of("   tab-test", "tab-test"),
                Pair.of("a", "a")
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
                " ",
                "test sample",
                "",
                "many arguments",
        };
    }
}
