package arguments.other;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.EnumArgument;
import de.paul2708.commands.arguments.util.Pair;

import java.time.DayOfWeek;

/**
 * This class tests the enum command argument.
 *
 * @author Paul2708
 */
public class EnumArgumentTest extends AbstractArgumentTest {

    // TODO: Test auto completion

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new EnumArgument<>(DayOfWeek.class);
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        return new Pair[] {
                Pair.of("monday", DayOfWeek.MONDAY),
                Pair.of("SATURDAY", DayOfWeek.SATURDAY),
                Pair.of("SunDaY", DayOfWeek.SUNDAY)
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
                "mOnTag",
                "MonDayy",
                "DayOfWeek.SUNDAY"
        };
    }
}
