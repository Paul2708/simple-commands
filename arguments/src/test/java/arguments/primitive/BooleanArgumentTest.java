package arguments.primitive;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.primitive.BooleanArgument;
import de.paul2708.commands.arguments.impl.primitive.CharacterArgument;
import de.paul2708.commands.arguments.util.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * This class tests the boolean command argument.
 *
 * @author Paul2708
 */
public class BooleanArgumentTest extends AbstractArgumentTest {

    /**
     * Create a new command argument.
     *
     * @return command argument.
     */
    @Override
    public CommandArgument<?> create() {
        return new BooleanArgument();
    }

    /**
     * Get an array of pairs, that provide the correct object to the argument.
     *
     * @return array of pairs
     */
    @Override
    public Pair[] validArguments() {
        List<Pair<String, Boolean>> pairs = new LinkedList<>();

        for (String trueKey : BooleanArgument.TRUE_KEYS) {
            pairs.add(Pair.of(trueKey, true));
        }
        for (String falseKey : BooleanArgument.FALSE_KEYS) {
            pairs.add(Pair.of(falseKey, false));
        }

        pairs.add(Pair.of("tRuE", true));
        pairs.add(Pair.of("FALSE", false));

        Pair[] array = new Pair[pairs.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = pairs.get(i);
        }

        return array;
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
                "yes!!!!",
                "nonono"
        };
    }
}
