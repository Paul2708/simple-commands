package arguments.other;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.EnumArgument;
import de.paul2708.commands.arguments.util.Pair;

/**
 * This class tests an enum, that doesn't go along with the conventions.
 *
 * @author Paul2708
 */
public final class WeirdEnumArgumentTest extends AbstractArgumentTest {

    /**
     * Enum with different upper- and lower-sensitive constants.
     */
    private enum WeirdEnum {
        UPPERCASE, lowercase, mIxEd
    }

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new EnumArgument<>(WeirdEnum.class);
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("UPPERCASE", WeirdEnum.UPPERCASE),
                Pair.of("lowercase", WeirdEnum.lowercase),
                Pair.of("mIxEd", WeirdEnum.mIxEd),
                Pair.of("uppercase", WeirdEnum.UPPERCASE),
                Pair.of("LOWERCASE", WeirdEnum.lowercase),
                Pair.of("MiXeD", WeirdEnum.mIxEd)
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
                "not_defined",
                "WeirdEnum.lowercase"
        };
    }
}