package arguments;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.CharacterArgument;
import de.paul2708.commands.arguments.util.Pair;

/**
 * This class tests the character command argument.
 *
 * @author Paul2708
 */
public class CharacterArgumentTest extends AbstractArgumentTest {

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new CharacterArgument();
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("a", 'a'),
                Pair.of("A", 'A'),
                Pair.of("1", '1'),
                Pair.of("?", '?'),
                Pair.of(",", ',')
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
                "123",
                "word",
                "",
        };
    }
}
