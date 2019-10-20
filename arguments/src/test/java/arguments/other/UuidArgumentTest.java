package arguments.other;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.spigot.UuidArgument;
import de.paul2708.commands.arguments.util.Pair;

import java.util.UUID;
import java.util.function.Function;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 19.10.19
 */
public final class UuidArgumentTest extends AbstractArgumentTest {

    @Override
    public CommandArgument<?> create() {
        return new UuidArgument();
    }

    @Override
    public Pair[] validArguments() {
        Function<UUID, Pair<String, UUID>> makePair = uuid -> Pair.of(uuid.toString(), uuid);

        UUID first = UUID.randomUUID();
        UUID second = UUID.randomUUID();
        UUID third = UUID.randomUUID();
        return new Pair[]{
                makePair.apply(first),
                makePair.apply(second),
                makePair.apply(third),
        };
    }

    @Override
    public String[] invalidArguments() {
        return new String[]{"totally not a valid uuid", "0011-1232-1234-1234"};
    }
}