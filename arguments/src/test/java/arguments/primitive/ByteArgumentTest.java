package arguments.primitive;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.primitive.ByteArgument;
import de.paul2708.commands.arguments.util.Pair;

/**
 * This class tests the byte command argument.
 *
 * @author Paul2708
 */
public final class ByteArgumentTest extends AbstractArgumentTest {

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new ByteArgument();
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("0", (byte) 0),
                Pair.of("127", (byte) 127),
                Pair.of("13", (byte) 13),
                Pair.of("+100", (byte) 100),
                Pair.of("-128", (byte) -128),
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
                "128",
                "0,2",
                "test",
                "-129"
        };
    }
}
